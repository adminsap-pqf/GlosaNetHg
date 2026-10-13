import {animate, keyframes, style, transition, trigger} from '@angular/animations';
import {Component, ElementRef, EventEmitter, Input, OnInit, Output, Renderer, SimpleChange,} from '@angular/core';
import {FormControl, Validators} from '@angular/forms';

import {Calendar} from './calendar';
import * as moment from 'moment';
/* Dev Tools */
import {DEBUG} from "../../../settings/app.settings";
import {months} from "moment";
import * as _ from 'lodash';

interface DateFormatFunction {
  (date: Date): string;
}

interface ValidationResult {
  [key: string]: boolean;
}

@Component({
  selector: 'gn-date-picker',
  animations: [
    trigger('calendarAnimation', [
      transition('* => left', [
        animate(180, keyframes([
          style({transform: 'translateX(105%)', offset: 0.5}),
          style({transform: 'translateX(-130%)', offset: 0.51}),
          style({transform: 'translateX(0)', offset: 1})
        ]))
      ]),
      transition('* => right', [
        animate(180, keyframes([
          style({transform: 'translateX(-105%)', offset: 0.5}),
          style({transform: 'translateX(130%)', offset: 0.51}),
          style({transform: 'translateX(0)', offset: 1})
        ]))
      ])
    ])
  ],
  templateUrl: './date-picker.component.html',
  styleUrls: ['./date-picker.component.scss']
})
export class DatePickerComponent implements OnInit {

  private readonly DEFAULT_FORMAT = 'YYYY-MM-DD';

  @Input() cleanInput: boolean;

  @Input() top: any;
  @Input() sizeInput: any;
  @Input() weightInput: any;
  @Input() paddingInput: any;
  @Input() heightInput: any;
  @Input() lineColor: string = '';

  @Input() heightDatePicker: string = '';

  @Input() colorDate: string = '';
  @Input() textAlignInput: string = 'left';

  private dateVal: Date;
  // two way bindings
  @Output() dateChange = new EventEmitter<Date>();

  @Output() fecha: EventEmitter<any> = new EventEmitter<any>();
  @Output() fechaFormat: EventEmitter<any> = new EventEmitter<any>();

  @Input() get date(): Date {
    return this.dateVal;
  };

  set date(val: Date) {
    this.dateVal = val;
    this.dateChange.emit(val);
  }

  // api bindings
  @Input() disabled: boolean;
  @Input() accentColor: string;
  @Input() altInputStyle: boolean;
  @Input() dateFormat: string | DateFormatFunction;
  @Input() fontFamily: string;
  @Input() rangeStart: Date;
  @Input() rangeEnd: Date;
  // data
  @Input() placeholder: string = '';
  @Input() inputText: string;
  // view logic
  @Input() showCalendar: boolean;
  @Input() cancelText: string = 'CANCELAR';
  @Input() weekStart: number = 0;
  // events
  @Output() onSelect = new EventEmitter<Date>();
  // time
  @Input() calendarDays: Array<number>;
  @Input() currentMonth: string;
  @Input() dayNames: Array<String> = ['D', 'L', 'M', 'M', 'J', 'V', 'S']; // Default order: firstDayOfTheWeek = 0
  @Input() hoveredDay: Date;
  @Input() months: Array<string>;

  // Control para enviar la opción elegida
  @Input() field: string = '';
  @Output() onChange: EventEmitter<any> = new EventEmitter<any>();

  // Validar datos
  @Input() activeValidData : boolean = false;
  @Input() activeFirst : boolean = false;
  @Input() resetValidate: boolean = false;

  @Output() handleCalculateDate: EventEmitter<any> = new EventEmitter<any>();

  dayNamesOrdered: Array<String>;
  calendar: Calendar;
  currentMonthNumber: number;
  currentYear: number;
  currentDay: number;
  // animation
  animate: string;
  // colors
  colors: { [id: string]: string };
  // listeners
  clickListener: Function;
  // forms
  yearControl: FormControl;

  inputValue: number;

  validInput: boolean = false;
  activeValid: boolean = false;
  isOpen: boolean = false;

  mode:string = 'days';
  startTime: number = 0;
  endTime: number = 0;
  idRangeYear = -1;

  calendar_months:any = [
    {
      monthNumber: 0,
      monthText: 'ENERO',
      label: 'Ene',
      disable: false
    },
    {
      monthNumber: 1,
      monthText: 'FEBRERO',
      label: 'Feb',
      disable: false
    },
    {
      monthNumber: 2,
      monthText: 'MARZO',
      label: 'Mar',
      disable: false
    },
    {
      monthNumber: 3,
      monthText: 'ABRIL',
      label: 'Abr',
      disable: false
    },
    {
      monthNumber: 4,
      monthText: 'MAYO',
      label: 'May',
      disable: false
    },
    {
      monthNumber: 5,
      monthText: 'JUNIO',
      label: 'Jun',
      disable: false
    },
    {
      monthNumber: 6,
      monthText: 'JULIO',
      label: 'Jul',
      disable: false
    },
    {
      monthNumber: 7,
      monthText: 'AGOSTO',
      label: 'Ago',
      disable: false
    },
    {
      monthNumber: 8,
      monthText: 'SEPTIEMBRE',
      label: 'Sep',
      disable: false
    },
    {
      monthNumber: 9,
      monthText: 'OCTUBRE',
      label: 'Oct',
      disable: false
    },
    {
      monthNumber: 10,
      monthText: 'NOVIEMBRE',
      label: 'Nov',
      disable: false
    },
    {
      monthNumber: 11,
      monthText: 'DICIEMBRE',
      label: 'Dic',
      disable: false
    }
  ];

  calendar_years: any = [];

  rangeYears: any = [
    { start: 1950, end: 1959, id: 1 }, { start: 1960, end: 1969, id: 2 }, { start: 1970, end: 1979, id: 3 },
    { start: 1980, end: 1989, id: 4 }, { start: 1990, end: 1999, id: 5 }, { start: 2000, end: 2009, id: 6 },
    { start: 2010, end: 2019, id: 7 }, { start: 2020, end: 2029, id: 8 }, { start: 2030, end: 2039, id: 9 },
    { start: 2040, end: 2049, id: 10 }, { start: 2050, end: 2059, id: 11 }, { start: 2060, end: 2069, id: 12 },
    { start: 2070, end: 2079, id: 13 }, { start: 2080, end: 2089, id: 14 }, { start: 2090, end: 2099, id: 15 }
  ];

  constructor(private renderer: Renderer, private elementRef: ElementRef) {
    this.dateFormat = this.DEFAULT_FORMAT;
    // view logic
    this.showCalendar = false;
    // colors
    this.colors = {
      'black': '#333333',
      'blue': '#E2C913',
      'lightGrey': '#D5DEE2',
      'white': '#ffffff'
    };
    this.accentColor = this.colors['blue'];
    this.altInputStyle = false;
    // time
    this.updateDayNames();

    this.months = [
      'ENERO', 'FEBRERO', 'MARZO', 'ABRIL', 'MAYO', 'JUNIO', 'JULIO',
      'AGOSTO', 'SEPTIEMBRE', 'OCTUBRE', 'NOVIEMBRE', 'DICIEMBRE'
    ];
    // listeners
    this.clickListener = renderer.listenGlobal(
      'document',
      'click',
      (event: MouseEvent) => this.handleGlobalClick(event)
    );
    // form controls
    this.yearControl = new FormControl('', Validators.compose([
      Validators.required,
      Validators.maxLength(4),
      this.yearValidator,
      this.inRangeValidator.bind(this)
    ]));

    let date = new Date();
    date.setDate(date.getDate() - 1);
    //this.rangeStart = date;
  }

  ngOnInit() {
    this.updateDayNames();
    this.syncVisualsWithDate();
    this.activeValid = this.activeFirst;
  }

  ngOnChanges(changes: { [propertyName: string]: SimpleChange }) {
    if ((changes['date'] || changes['dateFormat'])) {
      this.syncVisualsWithDate();
    }

    if (changes['firstDayOfTheWeek'] || changes['dayNames']) {
      this.updateDayNames();
    }
    // DEBUG && console.log("--------------------------fecha valor-------------------------");
    // DEBUG && console.log(changes['date'].currentValue);
    if (changes['date'] !== undefined) { //cambio podria afectar a demas vistas con datepickers!!!!! <-----   ****************************************************************************
      if (changes['date'].currentValue !== null) {
        this.validInput = true;
      } else {
        this.validInput = false;
      }
    }
    if (changes.resetValidate) {
      if (changes.resetValidate.currentValue !== undefined && !changes.firstChange && changes.resetValidate.currentValue) {
        this.activeValid = false;
        this.isOpen = false;
      }
    }

  }

  ngOnDestroy() {
    this.clickListener();
  }

  // -------------------------------------------------------------------------------- //
  // -------------------------------- State Management ------------------------------ //
  // -------------------------------------------------------------------------------- //
  /**
   * Closes the calendar and syncs visual components with selected or current date.
   * This way if a user opens the calendar with this month, scrolls to two months from now,
   * closes the calendar, then reopens it, it will open with the current month
   * or month associated with the selected date
   */
  closeCalendar(): void {
    DEBUG && console.log('@closeCalendar');
    if (this.activeValidData && this.isOpen) {
      this.activeValid = this.inputText ? false : true;
    }
    this.showCalendar = false;
    this.syncVisualsWithDate();
    this.mode = 'days';
  }

  /**
   * Sets the date values associated with the ui
   */
  private setCurrentValues(date: Date) {
    this.currentMonthNumber = date.getMonth();
    this.currentMonth = this.months[this.currentMonthNumber];

    this.currentYear = date.getFullYear();
    this.inputValue = this.currentYear;

    const calendarArray = this.calendar.monthDays(this.currentYear, this.currentMonthNumber);
    this.calendarDays = [].concat.apply([], calendarArray);
    this.calendarDays = this.filterInvalidDays(this.calendarDays);
  }

  /**
   * Update the day names order. The order can be modified with the firstDayOfTheWeek input, while 0 means that the
   * first day will be sunday.
   */
  private updateDayNames() {
    this.dayNamesOrdered = this.dayNames.slice(); // Copy DayNames with default value (weekStart = 0)
    if (this.weekStart < 0 || this.weekStart >= this.dayNamesOrdered.length) {
      // Out of range
      throw Error(`The weekStart is not in range between ${0} and ${this.dayNamesOrdered.length - 1}`)
    } else {
      this.calendar = new Calendar(this.weekStart);
      this.dayNamesOrdered = this.dayNamesOrdered.slice(this.weekStart, this.dayNamesOrdered.length)
        .concat(this.dayNamesOrdered.slice(0, this.weekStart)); // Append beginning to end
    }
  }

  /**
   * Visually syncs calendar and input to selected date or current day
   */
  syncVisualsWithDate(): void {
    if (this.date !== null) {
      this.setInputText(this.date);
      this.setCurrentValues(this.date);
    } else if (_.isDate(this.rangeStart) && _.isDate(this.rangeEnd)) {
      this.inputText = '';
      this.setCurrentValues(
        this.rangeStart.getTime() < new Date().getTime() && this.rangeEnd.getTime() > new Date().getTime()
          ? new Date()
          : this.rangeStart
      );
    } else {
      this.inputText = '';
      this.setCurrentValues(new Date());
    }
  }

  /**
   * Sets the currentMonth and creates new calendar days for the given month
   */
  setCurrentMonth(monthNumber: number): void {
    this.currentMonth = this.months[monthNumber];
    const calendarArray = this.calendar.monthDays(this.currentYear, this.currentMonthNumber);
    this.calendarDays = [].concat.apply([], calendarArray);
    this.calendarDays = this.filterInvalidDays(this.calendarDays);
  }

  /**
   * Sets the currentYear and FormControl value associated with the year
   */
  setCurrentYear(year: number): void {
    this.currentYear = year;
    this.inputValue = year;
  }

  /**
   * Sets the visible input text
   */
  setInputText(date: Date): void {
    let inputText = "";
    const dateFormat: string | DateFormatFunction = this.dateFormat;
    if (dateFormat === undefined || dateFormat === null) {
      inputText = moment(date).format(this.DEFAULT_FORMAT);
    } else if (typeof dateFormat === 'string') {
      inputText = moment(date).format(dateFormat);
    } else if (typeof dateFormat === 'function') {
      inputText = dateFormat(date);
    }
    //DEBUG && console.log(dateFormat);
    if (dateFormat !== null) {
      this.fecha.emit(inputText);

      let dia = inputText.slice(6, 8);
      let mes = inputText.slice(4, 6);
      let year = inputText.slice(0, 4);
      this.inputText = dia + "/" + this.mesString(mes) + "/" + year;
      this.fechaFormat.emit(this.inputText);
      this.validInput = true;
      this.currentDay = Number(dia);
      //this.inputText = inputText;

      if (this.field !== '') {
        /* TODO: Devuelve un objeto Date de la fecha seleccionado para realizar validaciones */
        this.onChange.emit({value: new Date(parseInt(year), parseInt(mes) - 1, parseInt(dia)), field: this.field});
      }

    } else {
      this.validInput = false;
    }
    /*
      DEBUG && console.log("Clase active en input datePicker");
      DEBUG && console.log(this.validInput);
    */
  }

  // -------------------------------------------------------------------------------- //
  // --------------------------------- Click Handlers ------------------------------- //
  // -------------------------------------------------------------------------------- //
  /**
   * Sets the date values associated with the calendar.
   * Triggers animation if the month changes
   */
  onArrowClick(direction: string): void {
    const currentMonth: number = this.currentMonthNumber;
    let newYear: number = this.currentYear;
    let newMonth: number;
    // sets the newMonth
    // changes newYear is necessary
    if (direction === 'left') {
      if (currentMonth === 0) {
        newYear = this.currentYear - 1;
        newMonth = 11;
      } else {
        newMonth = currentMonth - 1;
      }
    } else if (direction === 'right') {
      if (currentMonth === 11) {
        newYear = this.currentYear + 1;
        newMonth = 0;
      } else {
        newMonth = currentMonth + 1;
      }
    }
    // check if new date would be within range
    let newDate = new Date(newYear, newMonth);
    let newDateValid: boolean;

    if (direction === 'left') {
      newDateValid = !this.rangeStart || newDate.getTime() >= this.rangeStart.getTime() || newDate.getMonth() >= this.rangeStart.getMonth();
    } else if (direction === 'right') {
      newDateValid = !this.rangeEnd || newDate.getTime() <= this.rangeEnd.getTime();
    }
    DEBUG && console.log(newDateValid);
    if (newDateValid) {
      this.setCurrentYear(newYear);
      this.currentMonthNumber = newMonth;
      this.setCurrentMonth(newMonth);
      this.triggerAnimation(direction);
    }
  }

  /**
   * Check if a date is within the range.
   * @param date The date to check.
   * @return true if the date is within the range, false if not.
   */
  isDateValid(date: Date): boolean {
    return (!this.rangeStart || date.getTime() >= this.rangeStart.getTime()) &&
      (!this.rangeEnd || date.getTime() <= this.rangeEnd.getTime());
  }

  /**
   * Filter out the days that are not in the date range.
   * @param calendarDays The calendar days
   * @return {Array} The input with the invalid days replaced by 0
   */
  filterInvalidDays(calendarDays: Array<number>): Array<number> {
    let newCalendarDays = [];
    calendarDays.forEach((day: number | Date) => {
      if (day === 0 || !this.isDateValid(<Date>day)) {
        newCalendarDays.push(0)
      } else {
        newCalendarDays.push(day)
      }
    });
    return newCalendarDays;
  }

  /**
   * Closes the calendar when the cancel button is clicked
   */
  onCancel(): void {
    DEBUG && console.log('@onCancel');
    this.closeCalendar();
  }

  /**
   * Toggles the calendar when the date input is clicked
   */
  onInputClick(): void {
    this.isOpen = true;
    this.showCalendar = !this.showCalendar;
    this.handleCalculateDate.emit(false);
  }

  /**
   * Returns the font color for a day
   */
  onSelectDay(day: Date): void {
    if (this.isDateValid(day)) {
      this.date = day;
      this.onSelect.emit(day);
      this.showCalendar = !this.showCalendar;
    }
  }

  /**
   * Sets the current year and current month if the year from
   * yearControl is valid
   */
  onYearSubmit(): void {
    if (this.inputValue > 2050 || this.inputValue < 1900) {
      let dates = new Date();
      this.inputValue = dates.getFullYear();
    }
    if (+this.inputValue !== this.currentYear) {
      this.setCurrentYear(+this.inputValue);
      this.setCurrentMonth(this.currentMonthNumber);
    } else {
      this.inputValue = this.currentYear;
    }

  }

  // -------------------------------------------------------------------------------- //
  // ----------------------------------- Listeners ---------------------------------- //
  // -------------------------------------------------------------------------------- //
  /**
   * Closes the calendar if a click is not within the datepicker component
   */
  handleGlobalClick(event: MouseEvent): void {

    const withinElement = this.elementRef.nativeElement.contains(event.target);
    if (!this.elementRef.nativeElement.contains(event.target)) {
      DEBUG && console.log('@handleGlobalClick: Entre');
      this.closeCalendar();
    }
  }

  // -------------------------------------------------------------------------------- //
  // ----------------------------------- Helpers ------------------------------------ //
  // -------------------------------------------------------------------------------- //
  /**
   * Returns the background color for a day
   */
  getDayBackgroundColor(day: Date): string {
    let color = this.colors['white'];
    if (this.isChosenDay(day)) {
      color = this.accentColor;
    } else if (this.isCurrentDay(day)) {
      color = this.colors['lightGrey'];
    }
    return color;
  }

  /**
   * Returns the font color for a day
   */
  getDayFontColor(day: Date): string {
    let color = this.colors['black'];
    if (this.isChosenDay(day)) {
      color = this.colors['white'];
    }
    return color;
  }

  /**
   * Returns whether a day is the chosen day
   */
  isChosenDay(day: Date): boolean {
    if (day) {
      return this.date ? day.toDateString() === this.date.toDateString() : false;
    } else {
      return false;
    }
  }

  /**
   * Returns whether a day is the current calendar day
   */
  isCurrentDay(day: Date): boolean {
    if (day) {
      return day.toDateString() === new Date().toDateString();
    } else {
      return false;
    }
  }

  /**
   * Returns whether a day is the day currently being hovered
   */
  isHoveredDay(day: Date): boolean {
    return this.hoveredDay ? this.hoveredDay === day && !this.isChosenDay(day) : false;
  }

  /**
   * Triggers an animation and resets to initial state after the duration of the animation
   */
  triggerAnimation(direction: string): void {
    this.animate = direction;
    setTimeout(() => this.animate = 'reset', 185);
  }

  // -------------------------------------------------------------------------------- //
  // ---------------------------------- Validators ---------------------------------- //
  // -------------------------------------------------------------------------------- //
  /**
   * Validates that a value is within the 'rangeStart' and/or 'rangeEnd' if specified
   */
  inRangeValidator(control: FormControl): ValidationResult {
    const value = control.value;

    if (this.currentMonthNumber) {
      const tentativeDate = new Date(+value, this.currentMonthNumber);
      if (this.rangeStart && tentativeDate.getTime() < this.rangeStart.getTime()) {
        return {'yearBeforeRangeStart': true};
      }
      if (this.rangeEnd && tentativeDate.getTime() > this.rangeEnd.getTime()) {
        return {'yearAfterRangeEnd': true};
      }
      return null;
    }

    return {'currentMonthMissing': true};
  }

  /**
   * Validates that a value is a number greater than or equal to 1970
   */
  yearValidator(control: FormControl): ValidationResult {
    const value = control.value;
    const valid = !isNaN(value) && value >= 1970 && Math.floor(value) === +value;
    if (valid) {
      return null;
    }
    return {'invalidYear': true};
  }

  mesString(num: any): String {
    let mes;
    switch (num) {
      case '01':
        mes = 'Ene';
        break;
      case '02':
        mes = 'Feb';
        break;
      case '03':
        mes = 'Mar';
        break;
      case '04':
        mes = 'Abr';
        break;
      case '05':
        mes = 'May';
        break;
      case '06':
        mes = 'Jun';
        break;
      case '07':
        mes = 'Jul';
        break;
      case '08':
        mes = 'Ago';
        break;
      case '09':
        mes = 'Sep';
        break;
      case '10':
        mes = 'Oct';
        break;
      case '11':
        mes = 'Nov';
        break;
      case '12':
        mes = 'Dic';
        break;
      default:
        break;
    }
    return mes;
  }

  changeMode(): void {
    DEBUG && console.log('@changeMode: inicio');
    let mode = 'days';

    switch (this.mode) {
      case 'days':
        mode = 'months';
        break;

      case 'months':
        mode = 'days';
        break;
    }

    this.mode = mode;
    this.handleCalculateDate.emit(true);
  }

  onSelectedMonth(month) {
    DEBUG && console.log('@onSelectedMonth: inicio');

    this.currentMonthNumber = month;
    this.setCurrentMonth(month);

    setTimeout(() => {
      this.mode = 'days';
    }, 100);
  }

  activeYears() {
    DEBUG && console.log('@activeYears: inicio');
    const {start, end, id} = _.find(this.rangeYears, o => { return  o.start <= this.currentYear && o.end >= this.currentYear });

    if (start && end && id) {
      let options = [];
      setTimeout(() => {
        this.mode = 'years';
      }, 100);
      let year = start - 1;
      for (let i = 0; i < 12; i++) {
        options.push(year + i);
      }
      this.calendar_years = options;
      this.startTime = start;
      this.endTime = end;
      this.idRangeYear = id;
    }
  }

  changeYearSingle(direction) {
    DEBUG && console.log('@changeYearSingle: inicio');

    let newYear: number = this.currentYear;

    if (direction === 'up') {
      newYear = this.currentYear - 1;
    } else if (direction === 'down') {
      newYear = this.currentYear + 1;
    }

    if (newYear < 1950 || newYear > 2100) return;

    this.setCurrentYear(newYear);
  }

  changeYearCollection(direction) {
    DEBUG && console.log('@changeYearCollection: inicio');

    let newId: number = this.idRangeYear;

    if (direction === 'up') {
      newId = this.idRangeYear - 1;
    } else if (direction === 'down') {
      newId = this.idRangeYear + 1;
    }

    if (newId < 1 || newId > 15) return;

    const {start, end} = _.find(this.rangeYears, o => { return  o.id === newId });
    if (start && end) {
      let options = [];
      let year = start - 1;
      for (let i = 0; i < 12; i++) {
        options.push(year + i);
      }
      this.calendar_years = options;
      this.startTime = start;
      this.endTime = end;
      this.idRangeYear = newId;
    }
  }

  onSelectedYear(year) {
    DEBUG && console.log('@onSelectedYear: inicio');

    setTimeout(() => {
      this.mode = 'months';
    }, 100);
    this.setCurrentYear(year);
  }

}
