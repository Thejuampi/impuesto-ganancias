class IncomeTaxCalculator {

    def factor = 1.0

    def static final scales = [
            new Scale(threshold: 0, percentage: 0.05, base_fix_income: 0),
            new Scale(threshold: 33040.0, percentage: 0.09, base_fix_income: 1652),
            new Scale(threshold: 66080.0, percentage: 0.12, base_fix_income: 4625),
            new Scale(threshold: 99119.0, percentage: 0.15, base_fix_income: 8590),
            new Scale(threshold: 132159.0, percentage: 0.19, base_fix_income: 13546),
            new Scale(threshold: 198239.0, percentage: 0.23, base_fix_income: 26101),
            new Scale(threshold: 264318.0, percentage: 0.27, base_fix_income: 41299),
            new Scale(threshold: 396478.0, percentage: 0.31, base_fix_income: 76982),
            new Scale(threshold: 528637.0, percentage: 0.35, base_fix_income: 117951),
    ]

    def static final not_taxable = 85848.99 + 412075.14
    def static final max_contribution = 16598.31

    def calculate(BigDecimal gross_salary_per_month) {
        def net_salary = _net_salary_without_income_tax(gross_salary_per_month)
        def annual_net_salary = _annual_salary(net_salary)
        def not_taxable_amount = _not_taxable_amount()
        def subject_to_tax = _subject_to_tax(annual_net_salary, not_taxable_amount)
        def income_tax = _income_tax(subject_to_tax)

        return _monthly_tax(income_tax)
    }

    def _monthly_tax(BigDecimal income_tax) {
        def monthly_tax = income_tax / 13.0
        println("Monthly tax = $income_tax / 13 = $monthly_tax")
        return monthly_tax
    }

    def _income_tax(BigDecimal subject_to_tax) {
        def scale = find_scale(subject_to_tax)
        def variable_amount = (subject_to_tax - scale.threshold).max(0.0)
        def tax = scale.base_fix_income + scale.percentage*variable_amount

        println("Calculated total tax: $scale.base_fix_income + $scale.percentage*$variable_amount = $tax")

        return tax
    }

    def find_scale(BigDecimal subject_to_tax) {
        def scale = scales
                .findAll { subject_to_tax >= it.threshold }
                .max { it.threshold }

        println("Found scale ${scale}")
        return scale
    }

    def _subject_to_tax(BigDecimal annual_net_salary, BigDecimal not_taxable_amount) {
        def subject_to_tax = (annual_net_salary - not_taxable_amount).max(0.0)
        println("Amount subject to tax: ($annual_net_salary - $not_taxable_amount).max(0.0) = ${subject_to_tax}")
        return subject_to_tax
    }

    def _annual_salary(BigDecimal net_salary) {
        def annual_net_salary = net_salary * 13.0
        println("Annual net salary without income tax: $net_salary*13 = $annual_net_salary")
        return annual_net_salary
    }

    def _not_taxable_amount() {
        println("Annual not taxable amount: ${not_taxable*factor}")
        return not_taxable*factor
    }

    def _net_salary_without_income_tax(BigDecimal gross_salary_per_month) {
        def contribution = _contributions(gross_salary_per_month)
        def result = gross_salary_per_month - contribution
        println("Calculated net salary without income tax: $gross_salary_per_month - $contribution = ${result}")
        return result
    }

    def _contributions(BigDecimal gross_salary_per_month) {
        def tax_rate = 0.17
        def contribution = max_contribution.min(tax_rate * gross_salary_per_month)
        println("Calculated contribution: min($tax_rate*$gross_salary_per_month, $max_contribution) = $contribution")

        return contribution
    }
}
