import { provideRouter, RouterConfig }  from '@angular/router';

import { ModelComponent, ModelBrowserComponent, ModelExecutorComponent } from '../pages/index';

export const routes: RouterConfig = [
  {path: '', redirectTo: 'browser', pathMatch: 'full' }
  , {path: 'browser', component: ModelBrowserComponent }
  , { path: 'browser/:id', component: ModelBrowserComponent }
  , { path: 'model', component: ModelComponent }
  , { path: 'model/:id', component: ModelComponent }
  , { path: 'model/:action/:id', component: ModelComponent }
  , { path: 'modelexe/:id', component: ModelExecutorComponent }
];

export const modelserverRouterProviders = [
  provideRouter(routes)
];
