mod income_tax;
use income_tax::calculate;

fn main() {
  let gross_salary_per_month = 500_000.0;
  let tax = calculate(gross_salary_per_month);

  println!("tax = {}", tax);
}