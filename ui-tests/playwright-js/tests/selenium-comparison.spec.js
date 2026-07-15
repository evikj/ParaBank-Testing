import { test, expect } from '@playwright/test';

test.describe('Selenium vs Playwright Comparison Scenarios', () => {

    test.beforeEach(async ({ page }) => {
        await page.goto('https://parabank.parasoft.com/parabank/index.htm');
        await page.locator('input[name="username"]').fill('john');
        await page.locator('input[name="password"]').fill('demo');
        await page.getByRole('button', { name: 'Log In' }).click();
    });

    // UI-04: approved credit
    test('UI-04: Request Loan - Approved Logic', async ({ page }) => {
        await page.getByRole('link', { name: 'Request Loan' }).click();

        await page.locator('#amount').fill('1000');
        await page.locator('#downPayment').fill('200'); // 20%
        await page.getByRole('button', { name: 'Apply Now' }).click();

        // Playwright awaits approval
        await expect(page.locator('#loanStatus')).toHaveText('Approved');
    });

    // UI-06: Bill Pay
    test('UI-06: Bill Pay - Complex Form Handling', async ({ page }) => {
        await page.getByRole('link', { name: 'Bill Pay' }).click();

        //
        await page.locator('input[name="payee.name"]').fill('Electric Co');
        await page.locator('input[name="payee.address.street"]').fill('Main St 10');
        await page.locator('input[name="payee.address.city"]').fill('Skopje');
        await page.locator('input[name="payee.address.state"]').fill('MK');
        await page.locator('input[name="payee.address.zipCode"]').fill('1000');
        await page.locator('input[name="payee.phoneNumber"]').fill('070111222');
        await page.locator('input[name="payee.accountNumber"]').fill('12345');
        await page.locator('input[name="verifyAccount"]').fill('12345');
        await page.locator('input[name="amount"]').fill('50.00');

        await page.getByRole('button', { name: 'Send Payment' }).click();

        await expect(page.getByRole('heading', { name: 'Bill Payment Complete' })).toBeVisible();
    });

    // UI-08: Balance consistency (Multi-page check)
    test('UI-08: Balance Consistency - Overview vs Details', async ({ page }) => {
        // Overview
        await page.getByRole('link', { name: 'Accounts Overview' }).click();

        //first row
        const firstRowBalance = page.locator('#accountTable tbody tr').first().locator('td').nth(1);

        // wait until balance not empty
        await expect(firstRowBalance).toContainText('$', { timeout: 15000 });

        const overviewBalance = (await firstRowBalance.textContent())?.trim();
        console.log(`Overview Balance captured: ${overviewBalance}`);

        await page.locator('#accountTable tbody tr').first().locator('td a').click();

        const detailsBalanceLocator = page.locator('#balance');

        await expect(detailsBalanceLocator).toContainText('$', { timeout: 15000 });
        const detailsBalance = (await detailsBalanceLocator.textContent())?.trim();
        console.log(`Details Balance captured: ${detailsBalance}`);

        expect(overviewBalance).toBe(detailsBalance);
    });
});