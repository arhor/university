import React, { Suspense, lazy } from 'react';
import { BrowserRouter as Router, Switch, Route } from 'react-router-dom';
import Container from '@material-ui/core/Container';
import Loading from './Loading';
import HomeView from '../views/home';

export default function RouterView() {
  return (
    <Container>
      <main>
        <Suspense fallback={<Loading />}>
          <Switch>
            <Route path="/"           component={HomeView} exact />
            <Route path="/users"      component={lazy(() => import('./user/ListUserComponent'))} />
            <Route path="/add-user"   component={lazy(() => import('./user/AddUserComponent'))} />
            <Route path="/edit-user"  component={lazy(() => import('./user/EditUserComponent'))} />
            <Route path="/faculties"  component={lazy(() => import('../views/faculties'))} />
          </Switch>
        </Suspense>
      </main>
    </Container>
  );
}
