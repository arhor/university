import React, { Suspense } from 'react'
import { Switch, Route } from 'react-router-dom'
import Home from './views/home'

export default function RouterView() {
  return (
    <main>
      <Suspense fallback="<div>loading...</div>">
        <Switch>
          <Route exact path="/" component={Home} />
        </Switch>
      </Suspense>
    </main>
  )
}
