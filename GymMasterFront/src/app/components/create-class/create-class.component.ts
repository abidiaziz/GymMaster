import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { MatSnackBar } from '@angular/material/snack-bar';
import { JwtService } from 'src/app/service/jwt.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-create-class',
  templateUrl: './create-class.component.html',
  styleUrls: ['./create-class.component.scss']
})
export class CreateClassComponent implements OnInit {
  classForm: FormGroup;

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private snackBar: MatSnackBar,
    private service: JwtService,
  ) {
    this.classForm = this.fb.group({
      name: ['', Validators.required],
      description: [''],
      date: ['', Validators.required],
      startTime: ['', Validators.required],
      endTime: ['', Validators.required],
      maxCapacity: ['', [Validators.required, Validators.min(1)]],
      coachId: ['', Validators.required],
    });
  }

  ngOnInit() {}

  onSubmit() {
    if (this.classForm.valid) {
      const classData = this.classForm.value;
      console.log(classData);
      
      classData.customerIds = []; // Initialize with empty array

      this.service.createClass({
        name: classData.name,
        descrition: classData.description,
        startTime: classData.date + 'T' + classData.startTime,
        endTime: classData.date + 'T' + classData.endTime,
        maxCapacity: classData.maxCapacity,
        coachId: classData.coachId,
      }).subscribe(
        (response) => {
          this.snackBar.open('Class created successfully!', 'Close', {
            duration: 2000,
            panelClass: ['success-snackbar']
          });
          this.router.navigate(['calendar']);
        },
        (error) => {
          this.snackBar.open('Error creating class. Please try again.', 'Close', {
            duration: 2000,
            panelClass: ['error-snackbar']
          });
          console.error('Error creating class:', error);
        }
      );
    }
  }
}

