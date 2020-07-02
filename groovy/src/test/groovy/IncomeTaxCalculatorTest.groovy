import spock.lang.Specification;

class IncomeTaxCalculatorSpecifications extends Specification {

  final IncomeTaxCalculator calculator = new IncomeTaxCalculator(factor: 1.0)

  def 'Given a salary with no deductions, When calculate, Then result should be the expected' (BigDecimal salary, int expected) {
    expect:
      calculator.calculate(salary).intValue() == expected

    where:
      salary    | expected
      10_000.0  | 0
      30_000.0  | 0
      50_000.0  | 186
      70_000.0  | 3054
      90_000.0  | 7750
      110_000.0 | 14125
      130_000.0 | 21125
      150_000.0 | 28125
      200_000.0 | 45625
  }

  def 'Given a salary with no deductions and a factor of 1.2, When calculate, Then result should be the expected' (BigDecimal salary, int expected) {
    given:
      calculator.factor = 1.2

    expect:
      calculator.calculate(salary).intValue() == expected

    where:
      salary    | expected
      10_000.0  | 0
      30_000.0  | 0
      50_000.0  | 0
      70_000.0  | 1416
      90_000.0  | 5446
      110_000.0 | 11444
      130_000.0 | 18444
      150_000.0 | 25444
      200_000.0 | 42944
  }

}