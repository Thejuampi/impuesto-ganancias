struct Scale {
  threshold: f64,
  percentage: f64,
  base_fix_income: f64,
}

const SCALES: [Scale; 9] = [
  Scale { threshold: 0.0, percentage: 0.05, base_fix_income: 0.00 },
  Scale { threshold: 47_669.16, percentage: 0.09, base_fix_income: 2_383.46 },
  Scale { threshold: 95_338.32, percentage: 0.12, base_fix_income: 6_676.68 },
  Scale { threshold: 143_007.48, percentage: 0.15, base_fix_income: 12_393.98 },
  Scale { threshold: 190_676.65, percentage: 0.19, base_fix_income: 19_544.36 },
  Scale { threshold: 286_014.96, percentage: 0.23, base_fix_income: 37_658.64 },
  Scale { threshold: 381_353.28, percentage: 0.27, base_fix_income: 59_586.45 },
  Scale { threshold: 572_029.92, percentage: 0.31, base_fix_income: 111_069.14 },
  Scale { threshold: 762_706.57, percentage: 0.35, base_fix_income: 170_178.90 },
];

const FACTOR: f64 = 1.0;
const NOT_TAXABLE: f64 = 123_861.17 + 594_533.62;
const MAX_CONTRIBUTION: f64 = 27034.90;

pub fn calculate(gross_salary_per_month: f64) -> f64 {
  let net_salary = dbg!(_net_salary_without_income_tax(gross_salary_per_month));
  let annual_net_salary = dbg!(_annual_salary(net_salary));
  let not_taxable_amount = dbg!(_not_taxable_amount());
  let subject_to_tax = dbg!(_subject_to_tax(annual_net_salary, not_taxable_amount));
  let income_tax = dbg!(_income_tax(subject_to_tax));

  return dbg!(_monthly_tax(income_tax));
}

fn _monthly_tax(income_tax: f64) -> f64 {
  let monthly_tax = income_tax / 12.0;

  return monthly_tax;
}

fn _income_tax(subject_to_tax: f64) -> f64 {
  let scale = find_scale(subject_to_tax);
  let variable_amount = (subject_to_tax - scale.threshold).max(0.0);
  let tax = scale.base_fix_income + scale.percentage * variable_amount;

  return tax;
}

//I still don't catch what is this 'a about
fn find_scale<'a>(subject_to_tax: f64) -> &'a Scale {
  return SCALES.iter()
    .filter(|it| subject_to_tax >= it.threshold)
    .max_by(|left, right| left.threshold.partial_cmp(&right.threshold).unwrap())
    .unwrap();
}

fn _subject_to_tax(annual_net_salary: f64, not_taxable_amount: f64) -> f64 {
  return (annual_net_salary - not_taxable_amount).max(0.0);
}

fn _annual_salary(net_salary: f64) -> f64 {
  return net_salary * 13.0;
}

fn _not_taxable_amount() -> f64 {
  return NOT_TAXABLE * FACTOR;
}

fn _net_salary_without_income_tax(gross_salary_per_month: f64) -> f64 {
  let contribution = _contributions(gross_salary_per_month);
  return gross_salary_per_month - contribution;
}

fn _contributions(gross_salary_per_month: f64) -> f64 {
  let tax_rate = 0.17;
  let contribution = MAX_CONTRIBUTION.min(tax_rate * gross_salary_per_month);

  return contribution;
}