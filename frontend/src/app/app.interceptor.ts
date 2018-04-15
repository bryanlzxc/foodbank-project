import { Injectable } from '@angular/core';
import {
    HttpRequest,
    HttpHandler,
    HttpEvent,
    HttpInterceptor,
    HttpResponse,
    HttpErrorResponse
} from '@angular/common/http';
import { Router } from '@angular/router';
import { ProgressService } from '@services/services';
import { Observable } from 'rxjs/Observable';
import Globals from '@config/globals';
import 'rxjs/add/operator/catch';
import 'rxjs/add/observable/throw';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

    constructor (private router: Router, private progSvc: ProgressService) {}

    /* INTERCEPT ALL HTTP REQUESTS AND SETS AUTHORIZATION TOKEN */
    public intercept (request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        let session = JSON.parse(sessionStorage.getItem(Globals.SESSION_STORAGE));
        let token = session ? session.token : null;
        if (token) {
            request = request.clone({ setHeaders: { Authorization: token } });
        }
        return next.handle(request)
            .catch((err: any, caught) => {
                if (err instanceof HttpErrorResponse) {
                    this.progSvc.clear();
                    if (err.status === 403 || err.status === 500) {
                        sessionStorage.removeItem(Globals.SESSION_STORAGE);
                        this.router.navigate(['/sessions/login']);
                    }
                    return Observable.throw(err);
                }
            });
    }

}
