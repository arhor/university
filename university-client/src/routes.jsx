import React, { Suspense } from 'react';
import { Switch, Route } from 'react-router-dom';
import Home from './views/home';

const Loading = () => <div>loading...</div>;

export default function RouterView() {
  return (
    <main className="col-md-9 ml-sm-auto col-lg-10 px-4" role="main">
      <Suspense fallback={<Loading />}>
        <Switch>
          <Route exact path="/" component={Home} />
        </Switch>
      </Suspense>
    </main>
  );
}
