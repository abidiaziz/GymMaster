import { Component, OnInit } from '@angular/core';
import { User } from '../users.component';
import { JwtService } from 'src/app/service/jwt.service';
import { Route, Router } from '@angular/router';

@Component({
  selector: 'app-user-details',
  templateUrl: './user-details.component.html',
  styleUrls: ['./user-details.component.scss']
})
export class UserDetailsComponent implements OnInit {
  user: User | null = null;

  constructor(private service: JwtService, private readonly router: Router) {}

  ngOnInit(): void {
    this.user = this.service.selectedUser();
    
    if(!this.user) {
      const userId = this.router.parseUrl(this.router.url).root.children['primary'].segments[1].path;
      this.service.getUserById(userId).subscribe((res) => {
        this.user = res;
      })
    }  
  }
}

