export interface ColumnInterface {
    activeIco: boolean;
    activeTooltip: boolean;
    isDate: boolean;
    isGroup: boolean;
    isMoney: boolean;
    isComment: boolean;
    isLink: boolean;
    isDescription: boolean;
    label: string;
    subLabel: string;
    comment: string;
    linkType: string;
    parent: any;
    isStatus: boolean;
    status: number;
    activeTooltipError: boolean;
    errorMessage: string;
}
