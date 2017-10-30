import { Component,OnInit } from '@angular/core';
import { CustomValidators } from 'ng2-validation';
import { FormGroup, FormControl, Validators } from '@angular/forms';

@Component({
    selector: 'vol-inv',
    templateUrl: './inventory.component.html'
})

export class VolunteerInventoryComponent implements OnInit{
  selectedValue: string = "Pizza";
  selectedValue2: string;

  donors = [
    { value: 'steak-0', viewValue: 'Donors' },
    { value: 'steak-0', viewValue: 'Royal Park Hotel' },
    { value: 'steak-1', viewValue: 'ABC Bakery' },
    { value: 'steak-2', viewValue: 'Tim Ho Wan' }
  ];

  categories = [
    { value: 'steak-0', viewValue: 'Staples (Rice)' },
    { value: 'steak-1', viewValue: 'Meat' },
    { value: 'steak-2', viewValue: 'Pasta' }
  ];

  weights = [
    { value: 'steak-0', viewValue: '50g' },
    { value: 'steak-1', viewValue: '100g' },
    { value: 'steak-2', viewValue: '150g' },
    { value: 'steak-3', viewValue: '200g' },
    { value: 'steak-4', viewValue: '250g' },
    { value: 'steak-5', viewValue: '300g' },
    { value: 'steak-6', viewValue: '350g' },
    { value: 'steak-7', viewValue: '400g' },
    { value: 'steak-8', viewValue: '450g' },
    { value: 'steak-9', viewValue: '500g' },
  ];

  foods = [
    { value: 'test-0', viewValue: 'Steak' },
    { value: 'test-1', viewValue: 'Pizza' },
    { value: 'test-2', viewValue: 'Tacos' }
  ];

  formData = {}
  console = console;
  basicForm: FormGroup;

  constructor() { }

  ngOnInit() {
    let password = new FormControl('', Validators.required);
    let confirmPassword = new FormControl('', CustomValidators.equalTo(password));

    this.basicForm = new FormGroup({
      username: new FormControl('', [
        Validators.minLength(4),
        Validators.maxLength(9)
      ]),
      firstname: new FormControl('', [
        Validators.required
      ]),
      email: new FormControl('', [
        Validators.required,
        Validators.email
      ]),
      website: new FormControl('', CustomValidators.url),
      date: new FormControl(),
      cardno: new FormControl('', [
        Validators.required,
        CustomValidators.creditCard
      ]),
      phone: new FormControl('', CustomValidators.phone('BD')),
      password: password,
      confirmPassword: confirmPassword,
      gender: new FormControl('', [
        Validators.required
      ]),
      isAgreed: new FormControl('', [
        Validators.required
      ]),
    })
  }
}
