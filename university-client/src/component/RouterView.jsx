import React, { Suspense, lazy } from 'react';
import { BrowserRouter as Router, Switch, Route } from 'react-router-dom';
import Home from '../views/home';

const Loading = () => <div>loading...</div>;

const style = {
  marginTop: '20px',
};

export default function RouterView() {
  return (
    <main style={style}>
      <Router>
        <Suspense fallback={<Loading />}>
          <Switch>
            <Route path="/"          component={Home} exact />
            <Route path="/users"     component={lazy(() => import('./user/ListUserComponent'))} />
            <Route path="/add-user"  component={lazy(() => import('./user/AddUserComponent'))} />
            <Route path="/edit-user" component={lazy(() => import('./user/EditUserComponent'))} />
          </Switch>
        </Suspense>
      </Router>
    </main>
  );
}
