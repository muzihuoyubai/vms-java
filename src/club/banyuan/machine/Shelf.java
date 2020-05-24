package club.banyuan.machine;

public class Shelf {

  private String productName;
  private int price;
  private int inventory;
  /**
   * 编号，A B C D E，由售货机指定
   */
  private String code;

  public Shelf() {
  }

  public Shelf(String productName, int price, int inventory) {
    this.productName = productName;
    this.price = price;
    this.inventory = inventory;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public int getPrice() {
    return price;
  }

  public void setPrice(int price) {
    this.price = price;
  }

  public String getProductName() {
    return productName;
  }

  public void setProductName(String productName) {
    this.productName = productName;
  }

  public int getInventory() {
    return inventory;
  }

  public void setInventory(int inventory) {
    this.inventory = inventory;
  }
}
