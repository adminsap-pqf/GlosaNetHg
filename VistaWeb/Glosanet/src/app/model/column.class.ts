export class ColumnClass {
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

    constructor() {
        this.activeIco = false;
        this.activeTooltip = false;
        this.isDate = false;
        this.isGroup = false;
        this.isMoney = false;
        this.isComment = false;
        this.isLink = false;
        this.isDescription = false;
        this.label = '';
        this.subLabel = '';
        this.comment = '';
        this.linkType = '';
        this.parent = {};
        this.isStatus = false;
        this.status = -1;
        this.activeTooltipError = false;
        this.errorMessage = '';
    }
}

export const headsFactura = [
    { label: '#' }, { label: 'Factura' }, { label: 'Fecha Facturación' },
    { label: 'Incoterm' }, { label: 'Proveedor Mercancía' }, { label: 'Proveedor Mercancía Detalle' },
    { label: 'PDF' }
];

export const settingsFactura = [
    {
        width: '50px',
        align: 'center'
    },
    {
        width: '130px',
        align: 'flex-start'
    },
    {
        width: '100px',
        align: 'center'
    },
    {
        width: '100px',
        align: 'center'
    },
    {
        width: '130px',
        align: 'center'
    },
    {
        width: '130px',
        align: 'center'
    },
    {
        width: '100px',
        align: 'center'
    }
];

export const headsE_Doc = [
    { label: '#' }, { label: 'Clave Caso' },
    { label: 'Identificador de Caso' },
    { label: 'Tipo de documento' },
    { label: 'PDF' }, { label: 'Acuse SAT' }
];

export const settingsE_Doc = [
    {
        width: '60px',
        align: 'center'
    },
    {
        width: '70px',
        align: 'center'
    },
    {
        width: '200px',
        align: 'center'
    },
    {
        width: '250px',
        align: 'center'
    },
    {
        width: '150px',
        align: 'center'
    },
    {
        width: '150px',
        align: 'center'
    }
];

export const headsPartida = [
    { label: '#' }, { label: 'Fracción' },
    { label: 'Secuencia Fracción' }, { label: 'Subdivisión Fracción' },
    { label: 'Descripción' }, { label: 'País Origen' },
    { label: 'País Comprador' }, { label: 'Monto' }
];

export const settingsPartida = [
    {
        width: '50px',
        align: 'center'
    },
    {
        width: '90px',
        align: 'center'
    },
    {
        width: '60px',
        align: 'center'
    },
    {
        width: '60px',
        align: 'center'
    },
    {
        width: '310px',
        align: 'flex-start'
    },
    {
        width: '60px',
        align: 'center'
    },
    {
        width: '60px',
        align: 'center'
    },
    {
        width: '110px',
        align: 'flex-end'
    }
];

export const headsPedimento = [
    { label: '#' }, { label: 'none' }, { label: 'Sección' }, { label: 'Pedimento' },
    { label: 'Patente' }, { label: 'Tipo Operación' }, { label: 'Clave Doc.' },
    { label: 'Tipo Pedimento' }, { label: 'Recepción Pedimento' }, { label: 'Fecha Pago Real' },
    { label: 'Facturas' }, { label: 'E-doc' }, { label: 'Partidas' }, { label: 'none' }
];

export const settingsPedimento = [
    {
        width: '50px',
        align: 'center'
    },
    {
        width: '50px',
        align: 'center'
    },
    {
        width: '120px',
        align: 'flex-start'
    },
    {
        width: '70px',
        align: 'center'
    },
    {
        width: '70px',
        align: 'center'
    },
    {
        width: '90px',
        align: 'center'
    },
    {
        width: '70px',
        align: 'center'
    },
    {
        width: '90px',
        align: 'center'
    },
    {
        width: '80px',
        align: 'center'
    },
    {
        width: '80px',
        align: 'center'
    },
    {
        width: '80px',
        align: 'center'
    },
    {
        width: '80px',
        align: 'center'
    },
    {
        width: '80px',
        align: 'center'
    },
    {
        width: '100px',
        align: 'center'
    }
 ];
