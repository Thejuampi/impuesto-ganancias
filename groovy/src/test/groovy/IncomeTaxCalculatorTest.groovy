import spock.lang.Specification;

class IncomeTaxCalculatorSpecifications extends Specification {

  final IncomeTaxCalculator calculator = new IncomeTaxCalculator()

  def '2019 values' (BigDecimal salary, int expected) {
    expect:
      calculator.calculate(salary, 2019).intValue() == expected

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

  def '2020 values' (BigDecimal salary, int expected) {
    expect:
    calculator.calculate(salary, 2020).intValue() == expected

    where:
    salary    | expected
    10_000.0  | 0
    30_000.0  | 0
    50_000.0  | 0
    70_000.0  | 153
    90_000.0  | 2971
    110_000.0 | 8290
    130_000.0 | 15189
    150_000.0 | 22189
    200_000.0 | 39689
  }

  def '2021 values without new increment during elections' (BigDecimal salary, int expected) {
    expect:
    calculator.calculate(salary, 2021).intValue() == expected

    where:
    salary    | expected
    200_000.0 | 32847
    300_000.0 | 67847
    400_000.0 | 102847
  }

  def '2022 values without increment during 2021 elections' (BigDecimal salary, int expected) {
    expect:
    calculator.calculate(salary, 2022).intValue() == expected

    where:
    salary    | expected
    200_000.0 | 19592
    300_000.0 | 54592
    400_000.0 | 89592
  }

}