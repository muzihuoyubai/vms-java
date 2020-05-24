package club.banyuan.machine;

import club.banyuan.menu.Menu;
import org.junit.Test;

public class VendingMachineTest {

  @Test
  public void displayTest(){
    VendingMachine vendingMachine = new VendingMachine();
    vendingMachine.display();
  }

  @Test
  public void menuTest(){
    VendingMachine vendingMachine = new VendingMachine();
    Menu<FlowStatus> menu = vendingMachine.getMenu();
    menu.display();
    menu.selectMenuFromInput();

    menu.display();

  }

  public static void main(String[] args) {
    VendingMachine vendingMachine = new VendingMachine();
    Menu<FlowStatus> menu = vendingMachine.getMenu();
    menu.display();
    menu.selectMenuFromInput();
    menu.toSelectedMenu();
    menu.display();
    menu.selectMenuFromInput();
    menu.toSelectedMenu();
    menu.display();
  }
}
