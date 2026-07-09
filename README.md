# ParaBank-Testing
Софтверски квалитет и тестирање - Проектна задача
Овој проект претставува сеопфатна верификација на квалитетот на апликацијата ParaBank преку автоматизирано тестирање на различни слоеви на системот. Целта е да се демонстрира примена на различни алатки и техники за откривање на функционални, логички и безбедносни дефекти.

Проектот опфаќа вкупно 24 уникатни тест сценарија поделени во три главни фази:

    API Тестирање (Backend): Верификација на REST услугите преку Postman.

    UI Тестирање (Frontend - Selenium): Функционално тестирање со примена на Page Object Model (POM).

    End-to-End Тестирање (Playwright): Комплексни кориснички текови и Cross-browser анализа (нова алатка).

🛠 Користени технологии

    Postman: Автоматизација на API барања и Request Chaining.

    Java & Selenium WebDriver: UI автоматизација структурирана во Maven проект со JUnit 5.

    Playwright (Node.js): Е2Е сценарија со користење на Trace Viewer и видео записи.

    GitHub: Верзионирање на код и менаџирање на тест-артефакти.

📁 Структура на репозиториумот

    api-tests/: Содржи Postman колекција и Environment фајлови.

    ui-tests/selenium-junit/: Изворен код на Selenium тестовите (Java/Maven).

    ui-tests/playwright-js/: Изворен код на Playwright E2E сценаријата (JavaScript).

    docs/: Финална PDF документација и артефакти (trace.zip, video.webm).

🚀 Инструкции за извршување
1. API Тестови (Postman)

    Увезете ги .json фајловите од папката api-tests во Postman.

    Изберете ја околината ParaBank-Prod.

    Стартувајте ја колекцијата преку Collection Runner.

2. Selenium Тестови

    Оворете го проектот во IntelliJ IDEA.

    Позиционирајте се во ui-tests/selenium-junit.

    Извршете ја командата: mvn test

3. Playwright Тестови

    Позиционирајте се во ui-tests/playwright-js.

    Инсталирајте зависности: npm install

    Извршете ги тестовите: npx playwright test

    За преглед на извештајот: npx playwright show-report

🔍 Клучни наоди (Defect Detection)

Во текот на тестирањето беа идентификувани 3 критични дефекти:

    BUG-01: Неограничен Overdraft (одење во минус) преку API слојот.

    BUG-02: Целосен прекин на Stock-сервисот (500 Internal Server Error).

    BUG-03: Нецелосна инвалидација на сесијата по Logout (Security risk).
