package club.banyuan.menu;

import club.banyuan.machine.FlowStatus;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Menu<FLOW extends Enum<FLOW>> {

  private final MenuNode<FLOW> root;
  private MenuNode<FLOW> curMenu;
  private String userInput;
  private String defaultInputTips = "Your choice:";

  public Menu(MenuBuilder<FLOW> flowMenuBuilder) {
    root = flowMenuBuilder.getAllNodes().get(0);
    curMenu = root;
  }

  public void display() {
    System.out.println(curMenu.getDisplayOnTop());
    for (MenuNode<FLOW> subMenu : curMenu.getSubMenu()) {
      System.out.println(subMenu.getDisplayOnChoose());
    }
  }

  public void selectMenuFromInput() {
    Scanner scanner = new Scanner(System.in);
    System.out.println();
    System.out.print(defaultInputTips);
    userInput = scanner.next();
    boolean isInputInvalid = false;
    MenuNode<FLOW> userChoice = null;
    for (MenuNode<FLOW> subMenu : curMenu.getSubMenu()) {
      if (subMenu.getInputMatches().equals(userInput)) {
        isInputInvalid = true;
        userChoice = subMenu;
        break;
      }
    }
    if (!isInputInvalid) {
      System.out.println("invalid choice");
      System.out.println();
      display();
      selectMenuFromInput();
    }
  }

  public void toSelectedMenu() {
    Optional<MenuNode<FLOW>> selectedNode = curMenu.getSubMenu().stream()
        .filter(t -> t.getInputMatches().equals(userInput)).findFirst();
    if (selectedNode.isPresent()) {
      if (selectedNode.get().getMenuType() == MenuType.GO_BACK) {
        curMenu = curMenu.getParentMenu();
      } else {
        curMenu = selectedNode.get();
      }
    } else {
      throw new RuntimeException("用户输入项不存在");
    }
  }

  public static <FLOW extends Enum<FLOW>> MenuBuilder<FLOW> builder() {
    return new MenuBuilder<>();
  }

  public static class MenuBuilder<FLOW extends Enum<FLOW>> {

    /**
     * 第一个节点为根节点
     */
    private List<MenuNode<FLOW>> allNodes = new ArrayList<>();

    public MenuBuilder<FLOW> addMenuNode(String displayOnChoose, String inputMatches,
        String displayOnTop,
        Enum<FLOW> flowStatus, MenuType menuType, Enum<FLOW> parentFlow) {
      MenuNode<FLOW> menuNode = new MenuNode<>(displayOnChoose, inputMatches, displayOnTop,
          flowStatus, menuType, parentFlow);
      Optional<MenuNode<FLOW>> parentNode = allNodes.stream().filter(t ->
          t.getFlowStatus() == menuNode.getParentFlow()).findFirst();
      parentNode.ifPresent(flowMenuNode -> flowMenuNode.addSubMenu(menuNode));
      allNodes.add(menuNode);
      return this;
    }

    public Menu<FLOW> build() {
      return new Menu<>(this);
    }

    public List<MenuNode<FLOW>> getAllNodes() {
      return allNodes;
    }

    public void setAllNodes(List<MenuNode<FLOW>> allNodes) {
      this.allNodes = allNodes;
    }
  }
  //
  //
  // public static void main(String[] args) {
  //   Menu<FlowStatus> menu = new Menu<>();
  //   menu.initMenu();
  //
  //   menu.display();
  //   menu.selectMenuFromInput();
  //   menu.display();
  // }
}
