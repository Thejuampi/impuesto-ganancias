class Scale {

  BigDecimal threshold
  BigDecimal percentage
  BigDecimal base_fix_income

  @Override
  public String toString() {
    return "Scale{" +
        "threshold=" + threshold +
        ", percentage=" + percentage +
        ", base_fix_income=" + base_fix_income +
        '}';
  }
}
