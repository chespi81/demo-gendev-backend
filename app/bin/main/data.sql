-- No need for auto-increment statements - Hibernate will set them to 1 by default

-- Insert test messages
INSERT INTO messages (content, timestamp) VALUES 
('Welcome to the banking app!', '2023-01-01 09:00:00'),
('New feature: You can now toggle your cards on/off', '2023-01-10 14:30:00'),
('System maintenance scheduled for 2023-02-15', '2023-01-20 11:15:00'),
('Security tip: Update your password regularly', '2023-02-05 13:45:00'),
('New mobile app version available', '2023-02-20 16:30:00'),
('Important: Verify your contact information', '2023-03-01 10:00:00');

-- Insert test accounts
INSERT INTO accounts (account_number, account_type, balance, owner_id, creation_date) VALUES 
('1234567890', 'Current', 5000.00, 'user123', '2022-01-01 09:00:00'),
('0987654321', 'Demand', 10000.00, 'user123', '2022-01-05 10:30:00'),
('5678901234', 'Current', 7500.00, 'user456', '2022-02-15 14:45:00'),
('4321098765', 'Demand', 15000.00, 'user456', '2022-02-20 16:15:00'),
-- New accounts with ownerID "12345678"
('7894561230', 'Current', 12500.00, '12345678', '2022-03-10 11:20:00'),
('1472583690', 'Demand', 8750.00, '12345678', '2022-03-15 14:30:00'),
('9638527410', 'Current', 3200.00, '12345678', '2022-04-05 09:45:00');

-- Insert test credit cards
INSERT INTO credit_cards (last_four_digits, card_name, valid_until, card_type, status, credit_limit, balance, owner_id) VALUES 
('4321', 'John Doe', '12/25', 'Credit', true, 5000.00, 3500.00, 'user123'),
('8765', 'John Doe', '10/26', 'Debit', true, 2000.00, 1800.00, 'user123'),
('1234', 'Jane Smith', '03/27', 'Credit', true, 10000.00, 7500.00, 'user456'),
('5678', 'Jane Smith', '06/24', 'Debit', false, 3000.00, 3000.00, 'user456'),
-- New cards with ownerID "12345678"
('9876', 'Carlos Rodriguez', '09/25', 'Credit', true, 8000.00, 6200.00, '12345678'),
('5432', 'Carlos Rodriguez', '05/26', 'Debit', true, 3000.00, 2500.00, '12345678'),
('2468', 'Carlos Rodriguez', '11/24', 'Credit', false, 15000.00, 15000.00, '12345678');

-- Insert transactions for card 1 (John's credit card)
INSERT INTO transactions (transaction_date, amount, description, type, credit_card_id) VALUES
('2023-01-05 14:23:15', 150.00, 'Restaurant Dinner', 'CHARGE', 1),
('2023-01-08 09:45:30', 89.99, 'Online Shopping', 'CHARGE', 1),
('2023-01-10 16:30:00', 200.00, 'Payment Received', 'CREDIT', 1),
('2023-01-15 12:10:45', 45.50, 'Groceries', 'CHARGE', 1),
('2023-01-20 18:22:05', 300.00, 'Electronics Store', 'CHARGE', 1);

-- Insert transactions for card 2 (John's debit card)
INSERT INTO transactions (transaction_date, amount, description, type, credit_card_id) VALUES
('2023-01-02 10:15:20', 25.00, 'Coffee Shop', 'CHARGE', 2),
('2023-01-09 13:20:45', 35.75, 'Book Store', 'CHARGE', 2),
('2023-01-17 09:30:15', 50.00, 'Gas Station', 'CHARGE', 2),
('2023-01-23 16:45:30', 75.50, 'Department Store', 'CHARGE', 2);

-- Insert transactions for card 3 (Jane's credit card)
INSERT INTO transactions (transaction_date, amount, description, type, credit_card_id) VALUES
('2023-01-03 11:30:25', 200.00, 'Clothing Store', 'CHARGE', 3),
('2023-01-07 15:20:10', 350.00, 'Flight Tickets', 'CHARGE', 3),
('2023-01-12 17:15:45', 500.00, 'Payment Received', 'CREDIT', 3),
('2023-01-18 14:40:30', 120.75, 'Restaurant', 'CHARGE', 3),
('2023-01-25 19:05:15', 800.00, 'Hotel Booking', 'CHARGE', 3);

-- Insert transactions for card 4 (Jane's debit card - inactive)
-- No transactions for this card as it is inactive

-- Insert transactions for card 5 (Carlos's credit card)
INSERT INTO transactions (transaction_date, amount, description, type, credit_card_id) VALUES
('2023-02-03 08:45:20', 245.75, 'Electronics Store', 'CHARGE', 5),
('2023-02-10 12:30:45', 89.90, 'Streaming Service', 'CHARGE', 5),
('2023-02-15 16:20:10', 500.00, 'Payment Received', 'CREDIT', 5),
('2023-02-18 19:15:30', 320.50, 'Home Improvement Store', 'CHARGE', 5),
('2023-02-25 11:40:15', 175.25, 'Restaurant', 'CHARGE', 5);

-- Insert transactions for card 6 (Carlos's debit card)
INSERT INTO transactions (transaction_date, amount, description, type, credit_card_id) VALUES
('2023-02-05 10:20:30', 42.50, 'Pharmacy', 'CHARGE', 6),
('2023-02-12 14:35:45', 65.80, 'Grocery Store', 'CHARGE', 6),
('2023-02-19 09:25:15', 30.00, 'Gas Station', 'CHARGE', 6),
('2023-02-26 16:50:40', 120.75, 'Department Store', 'CHARGE', 6);

-- Insert transactions for card 7 (Carlos's inactive credit card)
-- No transactions for this card as it is inactive

-- Insert account transactions for account 1 (John's current account)
INSERT INTO account_transactions (transaction_date, amount, description, type, account_id) VALUES
('2023-01-10 09:15:20', 500.00, 'Salary Deposit', 'CREDIT', 1),
('2023-01-12 14:30:45', 150.00, 'ATM Withdrawal', 'CHARGE', 1),
('2023-01-15 10:45:30', 75.50, 'Utility Bill Payment', 'CHARGE', 1),
('2023-01-20 16:20:15', 200.00, 'Transfer to Savings', 'CHARGE', 1),
('2023-01-25 09:30:00', 300.00, 'Client Deposit', 'CREDIT', 1),
('2023-01-28 11:45:20', 120.75, 'Online Purchase', 'CHARGE', 1),
('2023-02-01 14:20:30', 800.00, 'Salary Deposit', 'CREDIT', 1),
('2023-02-05 17:15:45', 250.00, 'ATM Withdrawal', 'CHARGE', 1),
('2023-02-10 09:40:15', 95.50, 'Internet Bill', 'CHARGE', 1),
('2023-02-15 13:25:30', 350.00, 'Transfer from Savings', 'CREDIT', 1),
('2023-02-18 16:50:20', 180.25, 'Grocery Shopping', 'CHARGE', 1),
('2023-02-22 10:15:40', 65.00, 'Mobile Bill Payment', 'CHARGE', 1),
('2023-02-25 15:30:10', 700.00, 'Client Deposit', 'CREDIT', 1),
('2023-03-01 09:45:25', 900.00, 'Salary Deposit', 'CREDIT', 1),
('2023-03-03 14:20:35', 200.00, 'ATM Withdrawal', 'CHARGE', 1),
('2023-03-07 11:35:15', 150.50, 'Electricity Bill', 'CHARGE', 1),
('2023-03-10 16:45:30', 85.75, 'Online Purchase', 'CHARGE', 1),
('2023-03-15 10:25:20', 400.00, 'Transfer Received', 'CREDIT', 1),
('2023-03-18 13:40:15', 120.00, 'Restaurant Payment', 'CHARGE', 1),
('2023-03-22 15:55:30', 75.50, 'Pharmacy Purchase', 'CHARGE', 1),
('2023-03-25 09:30:45', 200.00, 'ATM Withdrawal', 'CHARGE', 1),
('2023-03-28 14:15:10', 950.00, 'Bonus Deposit', 'CREDIT', 1),
('2023-04-01 10:45:25', 110.25, 'Insurance Payment', 'CHARGE', 1);

-- Insert account transactions for account 2 (John's demand account)
INSERT INTO account_transactions (transaction_date, amount, description, type, account_id) VALUES
('2023-01-05 11:20:15', 1000.00, 'Initial Deposit', 'CREDIT', 2),
('2023-01-15 13:45:30', 250.00, 'ATM Withdrawal', 'CHARGE', 2),
('2023-01-22 16:30:45', 500.00, 'Transfer Received', 'CREDIT', 2),
('2023-01-30 09:15:20', 300.00, 'Transfer to Current', 'CHARGE', 2),
('2023-02-05 14:40:10', 150.75, 'Online Purchase', 'CHARGE', 2),
('2023-02-12 10:25:35', 600.00, 'Deposit', 'CREDIT', 2),
('2023-02-20 15:50:45', 200.00, 'ATM Withdrawal', 'CHARGE', 2),
('2023-02-28 09:35:15', 450.00, 'Transfer Received', 'CREDIT', 2),
('2023-03-05 13:20:30', 75.50, 'Subscription Payment', 'CHARGE', 2),
('2023-03-12 16:45:10', 100.00, 'ATM Withdrawal', 'CHARGE', 2),
('2023-03-20 11:30:25', 800.00, 'Bonus Deposit', 'CREDIT', 2),
('2023-03-28 14:15:40', 350.25, 'Transfer to Savings', 'CHARGE', 2);

-- Insert account transactions for account 5 (Carlos's current account)
INSERT INTO account_transactions (transaction_date, amount, description, type, account_id) VALUES
('2023-02-01 10:15:20', 1500.00, 'Salary Deposit', 'CREDIT', 5),
('2023-02-05 15:30:45', 200.00, 'ATM Withdrawal', 'CHARGE', 5),
('2023-02-10 09:45:30', 85.75, 'Utility Bill Payment', 'CHARGE', 5),
('2023-02-15 14:20:15', 300.00, 'Transfer to Savings', 'CHARGE', 5),
('2023-02-20 11:35:00', 500.00, 'Client Deposit', 'CREDIT', 5),
('2023-02-25 16:45:20', 150.50, 'Online Purchase', 'CHARGE', 5),
('2023-03-01 09:30:30', 1800.00, 'Salary Deposit', 'CREDIT', 5),
('2023-03-05 13:15:45', 350.00, 'ATM Withdrawal', 'CHARGE', 5),
('2023-03-10 10:40:15', 110.25, 'Internet Bill', 'CHARGE', 5),
('2023-03-15 15:25:30', 450.00, 'Transfer from Savings', 'CREDIT', 5),
('2023-03-20 11:50:20', 220.75, 'Grocery Shopping', 'CHARGE', 5),
('2023-03-25 14:15:40', 95.00, 'Mobile Bill Payment', 'CHARGE', 5),
('2023-03-30 10:30:10', 900.00, 'Client Deposit', 'CREDIT', 5),
('2023-04-01 15:45:25', 1000.00, 'Salary Deposit', 'CREDIT', 5);

-- Insert account transactions for account 6 (Carlos's demand account)
INSERT INTO account_transactions (transaction_date, amount, description, type, account_id) VALUES
('2023-02-03 09:20:15', 2000.00, 'Initial Deposit', 'CREDIT', 6),
('2023-02-12 14:45:30', 300.00, 'ATM Withdrawal', 'CHARGE', 6),
('2023-02-18 11:30:45', 750.00, 'Transfer Received', 'CREDIT', 6),
('2023-02-24 15:15:20', 400.00, 'Transfer to Current', 'CHARGE', 6),
('2023-03-03 10:40:10', 180.25, 'Online Purchase', 'CHARGE', 6),
('2023-03-10 13:25:35', 800.00, 'Deposit', 'CREDIT', 6),
('2023-03-17 16:50:45', 250.00, 'ATM Withdrawal', 'CHARGE', 6),
('2023-03-24 11:35:15', 600.00, 'Transfer Received', 'CREDIT', 6),
('2023-03-31 14:20:30', 95.50, 'Subscription Payment', 'CHARGE', 6);
