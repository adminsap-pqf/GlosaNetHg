import { NgModule} from '@angular/core';
import { CommonModule } from '@angular/common';
import {
  AccountingPipe, AccountingFormatNumber, AccountingFormatMoney,
  DateFormatDot, DateFormatDot2, dateFormatSlash, dateFormatFull,
  Code, AccountingFormatNumber2Decimal, LineBreak, dateFormatNumber,
  dateFormatSlashFull, HighlightPipe, dateFormatNumberFull,
  AccountingFormatNumber3Decimal, SanitizeHtmlPipe, dateFormatCustom, FormatToken
} from './accounting.pipe';


@NgModule({
  imports: [
    CommonModule,
  ],
  declarations: [
    AccountingPipe,
    AccountingFormatNumber,
    AccountingFormatMoney,
    DateFormatDot,
    DateFormatDot2,
    dateFormatSlash,
    dateFormatFull,
    Code,
    LineBreak,
    AccountingFormatNumber2Decimal,
    dateFormatNumber,
    dateFormatSlashFull,
    HighlightPipe,
    dateFormatNumberFull,
    AccountingFormatNumber3Decimal,
    SanitizeHtmlPipe,
    dateFormatCustom,
    FormatToken
  ],
  providers: [
  ],
  exports: [
    AccountingPipe,
    AccountingFormatNumber,
    AccountingFormatMoney,
    DateFormatDot,
    DateFormatDot2,
    dateFormatSlash,
    dateFormatFull,
    Code,
    LineBreak,
    AccountingFormatNumber2Decimal,
    dateFormatNumber,
    dateFormatSlashFull,
    HighlightPipe,
    dateFormatNumberFull,
    AccountingFormatNumber3Decimal,
    SanitizeHtmlPipe,
    dateFormatCustom,
    FormatToken
  ]
})
export class PipeModule { }
