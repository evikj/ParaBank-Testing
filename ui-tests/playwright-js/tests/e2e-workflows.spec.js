import { test, expect } from '@playwright/test';

test.describe('End-to-End Business Workflows', () => {

    test.beforeEach(async ({ page }) => {

        test.setTimeout(90000);
        await page.goto('https://parabank.parasoft.com/parabank/index.htm');
        await page.locator('input[name="username"]').fill('john');
        await page.locator('input[name="password"]').fill('demo');
        await page.getByRole('button', { name: 'Log In' }).click();
        await expect(page.getByRole('heading', { name: 'Accounts Overview' })).toBeVisible();
    });

    test('E2E-01: Full lifecycle of a new savings account', async ({ page }) => {
        // 1. Open Account
        await page.getByRole('link', { name: 'Open New Account' }).click();
        await page.locator('#type').selectOption('1'); // Savings
        await page.waitForTimeout(1000);
        await page.getByRole('button', { name: 'Open New Account' }).click();

        const newAccountLocator = page.locator('#newAccountId');
        await expect(newAccountLocator).toBeVisible({ timeout: 20000 });
        const newAccountId = (await newAccountLocator.textContent())?.trim();
        console.log(`Captured New Account ID: ${newAccountId}`);

        // 2. Bill Pay
        await page.getByRole('link', { name: 'Bill Pay' }).click();

        await page.locator('input[name="payee.name"]').waitFor({ state: 'visible' });

        await page.locator('input[name="payee.name"]').fill('Cloud Service');
        await page.locator('input[name="payee.address.street"]').fill('Main St 123');
        await page.locator('input[name="payee.address.city"]').fill('Skopje');
        await page.locator('input[name="payee.address.state"]').fill('Macedonia');
        await page.locator('input[name="payee.address.zipCode"]').fill('1000');
        await page.locator('input[name="payee.phoneNumber"]').fill('070111222');
        await page.locator('input[name="payee.accountNumber"]').fill('99999');
        await page.locator('input[name="verifyAccount"]').fill('99999');
        await page.locator('input[name="amount"]').fill('25.00');

        // Избор на новата сметка од која се плаќа
        const fromAccDropdown = page.locator('select[name="fromAccountId"]');
        await expect(fromAccDropdown.locator(`option[value="${newAccountId}"]`)).toBeAttached({ timeout: 20000 });
        await fromAccDropdown.selectOption(newAccountId || "");

        await page.waitForTimeout(1000);
        await page.getByRole('button', { name: 'Send Payment' }).click();

        const successHeader = page.getByRole('heading', { name: 'Bill Payment Complete' });
        await expect(successHeader).toBeVisible({ timeout: 25000 });

        console.log("E2E-01: Bill Payment successful from new account.");
    });

    test('E2E-02: Loan processing and immediate fund transfer', async ({ page }) => {
        // 1. Get Loan
        await page.getByRole('link', { name: 'Request Loan' }).click();
        await page.locator('#amount').fill('500');
        await page.locator('#downPayment').fill('100');
        await page.getByRole('button', { name: 'Apply Now' }).click();

        // fix
        await expect(page.getByRole('heading', { name: 'Loan Request Processed' })).toBeVisible();
        const loanAccountLocator = page.locator('#newAccountId');
        const loanAccountId = (await loanAccountLocator.textContent())?.trim();

        if (!loanAccountId) throw new Error("Failed to capture Loan Account ID");
        console.log(`Captured Loan Account ID: ${loanAccountId}`);

        // 2. Transfer
        await page.getByRole('link', { name: 'Transfer Funds' }).click();

        // await account available
        const transferFromDropdown = page.locator('#fromAccountId');
        await expect(transferFromDropdown.locator(`option[value="${loanAccountId}"]`)).toBeAttached({ timeout: 20000 });

        await transferFromDropdown.selectOption(loanAccountId);
        await page.locator('#amount').fill('100');
        await page.getByRole('button', { name: 'Transfer' }).click();

        await expect(page.getByRole('heading', { name: 'Transfer Complete!' })).toBeVisible({ timeout: 20000 });
    });
});