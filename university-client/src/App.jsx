import React from 'react';
import { BrowserRouter as Router } from 'react-router-dom';
import RouterView from './component/RouterView';
import AppNavBar from './component/AppNavBar';

export default function App() {
  return (
    <Router>
      <AppNavBar />
      <RouterView />
    </Router>
  );
}
