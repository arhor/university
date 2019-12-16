import React, { Suspense } from 'react';
import { Switch, Route } from 'react-router-dom';
import Home from './views/home';

const Loading = () => <div>loading...</div>;

export default function RouterView() {
  return (
    <main className="container-fluid">
      <Suspense fallback={<Loading />}>
        <Switch>
          <Route exact path="/" component={Home} />
        </Switch>
      </Suspense>
    </main>
  );
}
