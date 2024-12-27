import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { JwtService } from 'src/app/service/jwt.service';
export type User = {
  id: string,
  name: string,
  phoneNumber: string,
  role: string,
  email: string,
}
@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.scss'],
})
export class UsersComponent {


  users: User[];

  constructor(
    public service: JwtService,
    private router: Router
  ) { }

  ngOnInit() {
    this.hello();
  }

  hello() {
    this.service.hello().subscribe(
      (response) => {
        console.log(response);
        this.users = response;
      }
    )
  }

  onSelectUser(user: User): void {
   this.service.selectedUser.set(user);
   this.router.navigateByUrl("/user/"+user.id);
  }

  navigate() {
    this.router.navigate(['register']);
  }
}
