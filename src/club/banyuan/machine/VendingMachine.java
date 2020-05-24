package club.banyuan.machine;

import club.banyuan.menu.Menu;
import club.banyuan.menu.Menu.MenuBuilder;
import club.banyuan.menu.MenuType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VendingMachine {

  /**
   * 一行货架数量固定为5，这里数量如果要调整，整体的显示样式都需要重写。
   */
  private static final int ONE_LINE_SHELVES_NUM = 5;

  private static final String BUTTON_AVAILABLE = "O";
  private static final String BUTTON_SELLOUT = "";
  private static final String BUTTON_DEFAULT = " ";
  private static final String WINDOW_DEFAULT = "=";

  private final List<Shelf> shelves;

  private Menu<FlowStatus> menu;
  /**
   * 售货机卖出货品的总金额
   */
  private int salesAmount;
  /**
   * 用户余额
   */
  private int userBalance;

  /**
   * 售出货物的窗口
   */
  private String salesWindow = WINDOW_DEFAULT;


  {
    /*
      初始化默认库存
      A. Juice ($10) (5 left)
      B. Cola($6)(1left)
      C. Tea ($5) (2 left)
      D. Water ($8) (1 left)
      E. Coffee ($7) (9 left)
     */
    shelves = new ArrayList<>();
    shelves.add(new Shelf("Juice", 10, 5));
    shelves.add(new Shelf("Cola", 6, 1));
    shelves.add(new Shelf("Tea", 5, 2));
    shelves.add(new Shelf("Water", 8, 1));
    shelves.add(new Shelf("Coffee", 7, 9));

    // 设置code
    for (int i = 0; i < shelves.size(); i++) {
      shelves.get(i).setCode(Character.toString('A' + i));
    }

    /*
    初始化菜单
     */
    // List<MenuNode<FlowStatus>> menus = new ArrayList<>(
    //     Arrays.asList(
    //         new MenuNode<>(null, null, "What would you like to do?", FlowStatus.ROOT,
    //             MenuType.HAS_SUB_MENU),
    //         new MenuNode<>("1. Read product information", "1",
    //             "(1) The displayed products are:",
    //             FlowStatus.READ_PRODUCT_INFO, MenuType.HAS_SUB_MENU, FlowStatus.ROOT),
    //         new MenuNode<>("2. Insert coin", "2", "(2) Which coin would you like to insert?",
    //             FlowStatus.INSERT_COIN, MenuType.HAS_SUB_MENU, FlowStatus.ROOT),
    //         new MenuNode<>("1. $1", "1", null,
    //             null, MenuType.NO_SUB_MENU, FlowStatus.INSERT_COIN),
    //         new MenuNode<>("2. $1", "2", null,
    //             null, MenuType.NO_SUB_MENU, FlowStatus.INSERT_COIN),
    //         new MenuNode<>("3. $5", "3", null,
    //             null, MenuType.NO_SUB_MENU, FlowStatus.INSERT_COIN),
    //         new MenuNode<>("4. $10", "4", null,
    //             null, MenuType.NO_SUB_MENU, FlowStatus.INSERT_COIN),
    //         new MenuNode<>("0. Go back", "0", null,
    //             null, MenuType.GO_BACK, FlowStatus.INSERT_COIN),
    //
    //         new MenuNode<>("3. Press product button", "3",
    //             "(3) Which product button would you like to press?",
    //             FlowStatus.PRESS_PRODUCT_BTN, MenuType.NO_SUB_MENU, FlowStatus.ROOT),
    //
    //         new MenuNode<>("4. Press return button", "4", "(4) Return button is pressed.",
    //             FlowStatus.PRESS_RETURN_BTN, MenuType.NO_SUB_MENU, FlowStatus.ROOT),
    //
    //         new MenuNode<>("9. Open service menu (code required)", "9",
    //             "(9) Opening service menu. Access code is required.",
    //             FlowStatus.SERVICE_MENU_OPEN, MenuType.HAS_SUB_MENU, FlowStatus.ROOT),
    //
    //         new MenuNode<>("1. Inspect machine status", "1", "(9-1) Machine status",
    //             FlowStatus.SERVICE_INSPECT_STATUS, MenuType.NO_SUB_MENU,
    //             FlowStatus.SERVICE_MENU_OPEN),
    //
    //         new MenuNode<>("2. Withdraw all money", "2",
    //             "(9-2) All money is being withdrawn.",
    //             FlowStatus.SERVICE_WITHDRAW_MONEY, MenuType.NO_SUB_MENU,
    //             FlowStatus.SERVICE_MENU_OPEN),
    //         new MenuNode<>("3. Refill product", "3",
    //             "(9-3) Which product would you like to refill?",
    //             FlowStatus.SERVICE_REFILL_PRODUCT, MenuType.NO_SUB_MENU,
    //             FlowStatus.SERVICE_MENU_OPEN),
    //         new MenuNode<>("4. Change product", "4",
    //             "(4-4) Which product would you like to change?",
    //             FlowStatus.SERVICE_REFILL_PRODUCT, MenuType.NO_SUB_MENU,
    //             FlowStatus.SERVICE_MENU_OPEN),
    //         new MenuNode<>("0. Go back", "0", null,
    //             FlowStatus.SERVICE_REFILL_PRODUCT, MenuType.NO_SUB_MENU,
    //             FlowStatus.SERVICE_MENU_OPEN),
    //
    //         new MenuNode<>("0. Quit", "0", null,
    //             FlowStatus.QUIT, MenuType.NO_SUB_MENU,
    //             FlowStatus.ROOT)
    //     )
    //
    // );

    initMenu();
  }

  public Menu<FlowStatus> getMenu() {
    return menu;
  }

  public void setMenu(Menu<FlowStatus> menu) {
    this.menu = menu;
  }

  private void initMenu() {
    MenuBuilder<FlowStatus> builder = Menu.builder();
    menu = builder.addMenuNode(null, null, "What would you like to do?", FlowStatus.ROOT,
        MenuType.HAS_SUB_MENU, null)
        .addMenuNode("1. Read product information", "1",
            "(1) The displayed products are:",
            FlowStatus.READ_PRODUCT_INFO, MenuType.HAS_SUB_MENU, FlowStatus.ROOT)
        .addMenuNode("2. Insert coin", "2", "(2) Which coin would you like to insert?",
            FlowStatus.INSERT_COIN, MenuType.HAS_SUB_MENU, FlowStatus.ROOT)
        .addMenuNode("1. $1", "1", null,
            null, MenuType.NO_SUB_MENU, FlowStatus.INSERT_COIN)
        .addMenuNode("2. $1", "2", null,
            null, MenuType.NO_SUB_MENU, FlowStatus.INSERT_COIN)
        .addMenuNode("3. $5", "3", null,
            null, MenuType.NO_SUB_MENU, FlowStatus.INSERT_COIN)
        .addMenuNode("4. $10", "4", null,
            null, MenuType.NO_SUB_MENU, FlowStatus.INSERT_COIN)
        .addMenuNode("0. Go back", "0", null,
            null, MenuType.GO_BACK, FlowStatus.INSERT_COIN)

        .addMenuNode("3. Press product button", "3",
            "(3) Which product button would you like to press?",
            FlowStatus.PRESS_PRODUCT_BTN, MenuType.NO_SUB_MENU, FlowStatus.ROOT)

        .addMenuNode("4. Press return button", "4", "(4) Return button is pressed.",
            FlowStatus.PRESS_RETURN_BTN, MenuType.NO_SUB_MENU, FlowStatus.ROOT)

        .addMenuNode("9. Open service menu (code required)", "9",
            "(9) Opening service menu. Access code is required.",
            FlowStatus.SERVICE_MENU_OPEN, MenuType.HAS_SUB_MENU, FlowStatus.ROOT)

        .addMenuNode("1. Inspect machine status", "1", "(9-1) Machine status",
            FlowStatus.SERVICE_INSPECT_STATUS, MenuType.NO_SUB_MENU,
            FlowStatus.SERVICE_MENU_OPEN)

        .addMenuNode("2. Withdraw all money", "2",
            "(9-2) All money is being withdrawn.",
            FlowStatus.SERVICE_WITHDRAW_MONEY, MenuType.NO_SUB_MENU,
            FlowStatus.SERVICE_MENU_OPEN)
        .addMenuNode("3. Refill product", "3",
            "(9-3) Which product would you like to refill?",
            FlowStatus.SERVICE_REFILL_PRODUCT, MenuType.NO_SUB_MENU,
            FlowStatus.SERVICE_MENU_OPEN)
        .addMenuNode("4. Change product", "4",
            "(4-4) Which product would you like to change?",
            FlowStatus.SERVICE_REFILL_PRODUCT, MenuType.NO_SUB_MENU,
            FlowStatus.SERVICE_MENU_OPEN)
        .addMenuNode("0. Go back", "0", null,
            FlowStatus.SERVICE_REFILL_PRODUCT, MenuType.NO_SUB_MENU,
            FlowStatus.SERVICE_MENU_OPEN)

        .addMenuNode("0. Quit", "0", null,
            FlowStatus.QUIT, MenuType.NO_SUB_MENU,
            FlowStatus.ROOT)
        .build();
  }

  public void display() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder
        .append("*---------------------------*").append(System.lineSeparator())
        .append("|     Vending   Machine     |").append(System.lineSeparator())
        .append("*---------------------------*").append(System.lineSeparator());

    buildShelfDisplay(stringBuilder);

    stringBuilder
        .append("*---------------------------*").append(System.lineSeparator())
        .append(String.format("|                    [$%2s]  |", userBalance))
        .append(System.lineSeparator())
        .append("|                           |").append(System.lineSeparator())
        .append(String.format("|           [=%s=]           |", salesWindow))
        .append(System.lineSeparator())
        .append("*---------------------------*").append(System.lineSeparator());
    System.out.println(stringBuilder.toString());
  }

  private void buildShelfDisplay(StringBuilder stringBuilder) {
    int fromIndex = 0;
    while (fromIndex < shelves.size()) {
      List<Shelf> oneLineShelves = this.shelves
          .subList(fromIndex, Math.min(fromIndex + ONE_LINE_SHELVES_NUM, this.shelves.size()));
      fromIndex += ONE_LINE_SHELVES_NUM;
      buildOneLineShelf(oneLineShelves, stringBuilder);
    }
  }

  /**
   * 只负责构建一行的显示内容
   *
   * @param shelves 如果传入的数量多余一行货架的数量，多余的将被忽略
   *                如果少于一行货架的数量，则不足的位置都是空白
   */
  private void buildOneLineShelf(List<Shelf> shelves, StringBuilder stringBuilder) {
    String[] codeTemplate = new String[ONE_LINE_SHELVES_NUM];
    String[] priceTemplate = new String[ONE_LINE_SHELVES_NUM];
    String[] buttonTemplate = new String[ONE_LINE_SHELVES_NUM];
    Arrays.fill(codeTemplate, " ");
    Arrays.fill(priceTemplate, "   ");
    Arrays.fill(buttonTemplate, "   ");

    for (int i = 0; i < shelves.size(); i++) {
      // 超出一行的数量，退出，余下的部分忽略
      if (i >= ONE_LINE_SHELVES_NUM) {
        break;
      }

      codeTemplate[i] = shelves.get(i).getCode();
      priceTemplate[i] = String.format("$%2s", shelves.get(i).getPrice());

      String buttonFlag = BUTTON_DEFAULT;
      if (shelves.get(i).getInventory() == 0) {
        buttonFlag = BUTTON_SELLOUT;
      } else if (userBalance > shelves.get(i).getPrice()) {
        buttonFlag = BUTTON_AVAILABLE;
      }
      buttonTemplate[i] = String.format("[%s]", buttonFlag);
    }

    stringBuilder.append(
        String.format("|   %s    %s    %s    %s    %s   |",
            codeTemplate[0],
            codeTemplate[1],
            codeTemplate[2],
            codeTemplate[3],
            codeTemplate[4]))
        .append(System.lineSeparator())
        .append(
            String.format("|  %s  %s  %s  %s  %s  |",
                priceTemplate[0],
                priceTemplate[1],
                priceTemplate[2],
                priceTemplate[3],
                priceTemplate[4])
        )
        .append(System.lineSeparator())
        .append(
            String.format("|  %s  %s  %s  %s  %s  |",
                buttonTemplate[0],
                buttonTemplate[1],
                buttonTemplate[2],
                buttonTemplate[3],
                buttonTemplate[4])
        )
        .append(System.lineSeparator())
    ;
  }

}
