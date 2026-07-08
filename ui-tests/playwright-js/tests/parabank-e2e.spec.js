import { test, expect } from '@playwright/test';

test('ParaBank Full E2E Flow', async ({ page }) => {

    test.setTimeout(80000);

    await page.goto('https://parabank.parasoft.com/parabank/index.htm');

    // 2. Login (UI-01)
    await page.locator('input[name="username"]').fill('john');
    await page.locator('input[name="password"]').fill('demo');
    await page.getByRole('button', { name: 'Log In' }).click();

    await expect(page.getByRole('heading', { name: 'Accounts Overview' })).toBeVisible({ timeout: 20000 });

    // 3. Open New Account (UI-03)
    await page.getByRole('link', { name: 'Open New Account' }).click();

    const typeDropdown = page.locator('#type');
    await typeDropdown.waitFor({ state: 'visible' });
    await typeDropdown.selectOption('1');

    await page.waitForTimeout(1500);
    await page.getByRole('button', { name: 'Open New Account' }).click();

    // Heading Role
    await expect(page.getByRole('heading', { name: 'Account Opened!' })).toBeVisible({ timeout: 20000 });

    // 4. Transfer Funds (UI-07/08)
    await page.getByRole('link', { name: 'Transfer Funds' }).click();

    const amountInput = page.locator('#amount');
    await amountInput.waitFor({ state: 'visible' });

    await page.locator('#fromAccountId option').first().waitFor({ state: 'attached' });
    await page.waitForTimeout(1000); // Безбедносна пауза за стабилност

    await amountInput.fill('10.00');
    await page.getByRole('button', { name: 'Transfer' }).click();

    // verification on successful transfer

    const successHeader = page.getByRole('heading', { name: 'Transfer Complete!' });
    await expect(successHeader).toBeVisible({ timeout: 25000 });

    // 5. Logout (UI-10)
    await page.getByRole('link', { name: 'Log Out' }).click();

    await expect(page.locator('input[name="username"]')).toBeVisible();
});