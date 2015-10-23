# REST API Documentation

All of the REST routes described here accept the additional
query parameter `raw`.

If `raw` is set with a truthy value (like "1"), this skips
the pre-processing of transactions (deduplication and
pretty-printing).

### GET /transactions

Returns the list of all transactions in the back-end system.

### GET /balance/:initial

Returns the final balance, provided an initial account balance
in parameter `initial`.

### GET /balance

Returns the final balance, assuming initial balance is zero.

### GET /expenses-by-ledger

This route accepts the query param `detailed`.

This groups transactions by ledger, and returns for each :
- the total expenses in the category.
- and all transactions in this category if detailed is set to
 a truthy value.

### GET /balance-by-day/:initial

Computes successive balances after each day. The account
initial value can be set in parameter `initial`.

### GET /balance-by-day

The same as the previous route, assuming the initial balance
is zero.
