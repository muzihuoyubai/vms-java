package club.banyuan.menu;

import java.util.ArrayList;
import java.util.List;

public class MenuNode<FLOW extends Enum<FLOW>> {

  /**
   * 展示子菜单项目之前展示
   */
  private String displayOnTop;
  /**
   * 和用户输入的内容必须匹配
   */
  private String inputMatches;
  /**
   * 父级菜单展示子菜单的内容
   */
  private String displayOnChoose;
  private MenuNode<FLOW> parentMenu;
  private List<MenuNode<FLOW>> subMenu;
  private MenuType menuType;
  /**
   * 菜单节点的FlowStatus不能重复, 可以为null
   */
  private Enum<FLOW> flowStatus;

  private Enum<FLOW> parentFlow;

  public MenuNode() {
  }

  public MenuNode(String displayOnChoose, String inputMatches, String displayOnTop,
      Enum<FLOW> flowStatus, MenuType menuType, Enum<FLOW> parentFlow) {
    this.displayOnTop = displayOnTop;
    this.inputMatches = inputMatches;
    this.displayOnChoose = displayOnChoose;
    this.menuType = menuType;
    this.flowStatus = flowStatus;
    this.parentFlow = parentFlow;
  }

  public MenuNode(String displayOnChoose, String inputMatches, String displayOnTop,
      Enum<FLOW> flowStatus, MenuType menuType) {
    this.displayOnTop = displayOnTop;
    this.inputMatches = inputMatches;
    this.displayOnChoose = displayOnChoose;
    this.menuType = menuType;
    this.flowStatus = flowStatus;
  }

  public MenuNode(String inputMatches, String displayOnChoose, Enum<FLOW> flowStatus) {
    this.inputMatches = inputMatches;
    this.displayOnChoose = displayOnChoose;
    this.flowStatus = flowStatus;
  }

  public MenuNode(String displayOnChoose, String inputMatches, String displayOnTop) {
    this.displayOnTop = displayOnTop;
    this.inputMatches = inputMatches;
    this.displayOnChoose = displayOnChoose;
  }

  public Enum<FLOW> getFlowStatus() {
    return flowStatus;
  }

  public void setFlowStatus(Enum<FLOW> flowStatus) {
    this.flowStatus = flowStatus;
  }

  public Enum<FLOW> getParentFlow() {
    return parentFlow;
  }

  public void setParentFlow(Enum<FLOW> parentFlow) {
    this.parentFlow = parentFlow;
  }

  public MenuType getMenuType() {
    return menuType;
  }

  public void setMenuType(MenuType menuType) {
    this.menuType = menuType;
  }

  public String getDisplayOnTop() {
    return displayOnTop;
  }

  public void setDisplayOnTop(String displayOnTop) {
    this.displayOnTop = displayOnTop;
  }

  public String getInputMatches() {
    return inputMatches;
  }

  public void setInputMatches(String inputMatches) {
    this.inputMatches = inputMatches;
  }

  public String getDisplayOnChoose() {
    return displayOnChoose;
  }

  public void setDisplayOnChoose(String displayOnChoose) {
    this.displayOnChoose = displayOnChoose;
  }

  public MenuNode<FLOW> getParentMenu() {
    return parentMenu;
  }

  public void setParentMenu(MenuNode<FLOW> parentMenu) {
    this.parentMenu = parentMenu;
  }

  public List<MenuNode<FLOW>> getSubMenu() {
    return subMenu;
  }

  public void setSubMenu(List<MenuNode<FLOW>> subMenu) {
    this.subMenu = subMenu;
  }

  public void addSubMenu(MenuNode<FLOW> menuNode) {
    if (this.subMenu == null) {
      subMenu = new ArrayList<>();
    }
    this.subMenu.add(menuNode);
    menuNode.setParentMenu(this);
  }
}
