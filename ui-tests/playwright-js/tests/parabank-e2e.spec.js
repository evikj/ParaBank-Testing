import { test, expect } from '@playwright/test';

test('ParaBank Full E2E Flow', async ({ page }) => {
    // Зголемен тајмаут за цел тест
    test.setTimeout(80000);

    // 1. Навигација
    await page.goto('https://parabank.parasoft.com/parabank/index.htm');

    // 2. Login (UI-01)
    await page.locator('input[name="username"]').fill('john');
    await page.locator('input[name="password"]').fill('demo');
    await page.getByRole('button', { name: 'Log In' }).click();

    // Верификација на најава преку насловот
    await expect(page.getByRole('heading', { name: 'Accounts Overview' })).toBeVisible({ timeout: 20000 });

    // 3. Open New Account (UI-03)
    await page.getByRole('link', { name: 'Open New Account' }).click();

    // Чекај го dropdown-от и избери SAVINGS
    const typeDropdown = page.locator('#type');
    await typeDropdown.waitFor({ state: 'visible' });
    await typeDropdown.selectOption('1');

    // Мала пауза за внатрешната логика на ParaBank
    await page.waitForTimeout(1500);
    await page.getByRole('button', { name: 'Open New Account' }).click();

    // Верификација со Heading Role
    await expect(page.getByRole('heading', { name: 'Account Opened!' })).toBeVisible({ timeout: 20000 });

    // 4. Transfer Funds (UI-07/08 логика)
    await page.getByRole('link', { name: 'Transfer Funds' }).click();

    const amountInput = page.locator('#amount');
    await amountInput.waitFor({ state: 'visible' });

    // НАМЕСТО waitForResponse: Чекаме додека првата опција во сметките не стане достапна
    // Ова гарантира дека податоците се вчитани во UI-то
    await page.locator('#fromAccountId option').first().waitFor({ state: 'attached' });
    await page.waitForTimeout(1000); // Безбедносна пауза за стабилност

    await amountInput.fill('10.00');
    await page.getByRole('button', { name: 'Transfer' }).click();

    // Верификација на успешен трансфер
    // Користиме Heading и подолг тајмаут бидејќи ParaBank е бавен при трансакции
    const successHeader = page.getByRole('heading', { name: 'Transfer Complete!' });
    await expect(successHeader).toBeVisible({ timeout: 25000 });

    // 5. Logout (UI-10)
    await page.getByRole('link', { name: 'Log Out' }).click();

    // Финална потврда дека сме одлогирани
    await expect(page.locator('input[name="username"]')).toBeVisible();
});