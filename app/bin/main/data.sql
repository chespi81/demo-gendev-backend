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
