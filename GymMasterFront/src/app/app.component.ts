import { Component, inject } from '@angular/core';
import { JwtService } from './service/jwt.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'jwt-angular';

  public readonly service = inject(JwtService);

  logout() {
    localStorage.clear();
    this.service.isLoggedIn.set(false);
  }
}
