from typing import NamedTuple


class PaymentSummary(NamedTuple):
    principal: float
    periodic_interest: float
    num_payments: int
    periodic_amount: float
    total_interest: float
    total_loan: float

    def __str__(self):
        return f"""
Loan APR (percent) {self.periodic_interest * 100 * 12:>12}
Loan term (years)  {self.num_payments // 12:>12}
Periodic amount    {self.periodic_amount:>12,.2f}

Principal          {self.principal:>12,.2f}
Total interest     {self.total_interest:>12,.2f}
Total loan amount  {self.total_loan:>12,.2f}\n
"""


class AmortizationPeriod(NamedTuple):
    number: int
    periodic_payment: float
    principal_payment: float
    interest_payment: float
    total_interest: float
    remaining_principal: float

    def __str__(self):
        return f"{self.number:>6} {self.periodic_payment:>11,.2f} {self.principal_payment:>11,.2f} {self.interest_payment:>11,.2f} {self.total_interest:>15,.2f} {self.remaining_principal:>20,.2f}"


def periodic_payment(P, i, n):
    """
    Return the periodic payment amount based on the annuity formula.

        A = P * i * (1 + i)^n / ((1 + i)^n - 1)

    A = periodic payment amount
    P = amount of principal, net of initial payments
    i = periodic interest rate
    n = total number of payments

    https://en.wikipedia.org/wiki/Amortization_calculator
    """
    m = pow(1 + i, n)
    return P * i * m / (m - 1)


def payment_summary(P, i, n):
    payment = periodic_payment(P, i, n)
    total_loan = payment * n
    total_interest = total_loan - P
    return PaymentSummary(P, i, n, payment, total_interest, total_loan)


def amortization_schedule(P, i, n):
    p = periodic_payment(P, i, n)
    remaining_principal = P

    periods = []
    total_interest = 0
    for period in range(n):
        periodic_interest = remaining_principal * i
        principal_payment = p - periodic_interest
        total_interest += periodic_interest
        remaining_principal -= principal_payment

        periods.append(
            AmortizationPeriod(
                number=period + 1,
                periodic_payment=p,
                principal_payment=principal_payment,
                interest_payment=periodic_interest,
                total_interest=total_interest,
                remaining_principal=max(0, remaining_principal),
            )
        )

    return periods


def amortization_table(P, i, n):
    res = [
        "Period     Payment   Principal    Interest  Total Interest  Remaining Principal",
        "-------------------------------------------------------------------------------",
    ]
    for period in amortization_schedule(P, i, n):
        res.append(str(period))

    return "\n".join(res)


if __name__ == "__main__":
    principal = 1_000_000.0
    loan_apr = 0.07
    loan_years = 30

    monthly_interest_rate = loan_apr / 12
    num_payments = loan_years * 12

    print(payment_summary(principal, monthly_interest_rate, num_payments))
    print(amortization_table(principal, monthly_interest_rate, num_payments))
