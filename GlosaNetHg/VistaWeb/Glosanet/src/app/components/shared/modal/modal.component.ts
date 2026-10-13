import {AfterViewInit, Component, Inject, OnInit, ViewChild, AfterViewChecked} from '@angular/core';
import {MatTableDataSource} from '@angular/material/table';
import {MatPaginator} from '@angular/material/paginator';
import {MAT_DIALOG_DATA} from '@angular/material/dialog';


export interface DialogData {
  animal: string;
  name: string;
}

@Component({
  selector: 'gn-modal',
  templateUrl: './modal.component.html',
  styleUrls: ['./modal.component.scss']
})
export class ModalComponent implements OnInit, AfterViewInit {
  displayedColumns = [
    'secuenciaFraccion',
    'indentFiscalProveedor',
    'proveedorMercancia',
    'paisCompradorVendedor',
    'valorAduana',
    'iva',
    'igi',
  ];
  dataSource: any = new MatTableDataSource<any>([]);
  @ViewChild(MatPaginator, {static: false}) paginator: MatPaginator;
  loadingDataStep5 = false;

  constructor(@Inject(MAT_DIALOG_DATA) public data: DialogData) {
    this.dataSource.data = data;
  }

  ngAfterViewInit() {
    this.paginator._intl.itemsPerPageLabel = 'items por pagina';
    this.paginator._intl.nextPageLabel = 'Siguiente';
    this.paginator._intl.previousPageLabel = 'Anterior';
    this.dataSource.paginator = this.paginator;
  }

  ngOnInit() {
  }

}
