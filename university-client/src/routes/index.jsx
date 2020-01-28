import React, { Suspense, lazy } from 'react';
import { Switch, Route } from 'react-router-dom';
import Container from '@material-ui/core/Container';
import Loading from 'components/Loading';
import HomeView from 'views/home';

export default function RouterView() {
  return (
    <Container>
      <main>
        <Suspense fallback={<Loading />}>
          <Switch>
            <Route path="/"          component={HomeView} exact />
            <Route path="/signin"    component={lazy(() => import('views/signin'))} />
            <Route path="/signup"    component={lazy(() => import('views/signup'))} />
            <Route path="/faculties" component={lazy(() => import('views/faculties'))} />
            <Route path="/users"     component={lazy(() => import('components/user/ListUserComponent'))} />
            <Route path="/edit-user" component={lazy(() => import('components/user/EditUserComponent'))} />
          </Switch>
        </Suspense>
      </main>
    </Container>
  );
}
