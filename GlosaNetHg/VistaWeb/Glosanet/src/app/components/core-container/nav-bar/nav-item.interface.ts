export interface NavItem {
  section: boolean;
  key: string;
  iconPath: string;
  translatedName: string;
  subMenuComponent: string;
  subMenuTitle ?: string;
  subMenuIcon ?: string;
  subMenuItems ?: Array<Object>;
}
