import {Injectable} from '@angular/core';
import {
  HttpEvent,
  HttpHandler,
  HttpInterceptor,
  HttpRequest
} from '@angular/common/http';

import {Observable, tap} from 'rxjs';
import {Router} from "@angular/router";

/** Inject With Credentials into the request */
@Injectable()
export class HttpRequestInterceptor implements HttpInterceptor {

  constructor(private router: Router) {
  }

  intercept(req: HttpRequest<any>, next: HttpHandler):
    Observable<HttpEvent<any>> {

    const requestWithHeader = req.clone({
      headers: req.headers.set('X-Requested-With', 'XMLHttpRequest'),
      withCredentials: true,
    });

    return next.handle(requestWithHeader).pipe(tap({
      next: () => {
      },
      error: (err: any) => {
        console.log(err)
      }
    }));

  }

}

