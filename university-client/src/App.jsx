import React from 'react';
import { BrowserRouter as Router } from 'react-router-dom';
import RouterView from 'routes';
import AppNavBar from 'components/AppNavBar';

export default function App() {
  return (
    <Router>
      <AppNavBar />
      <RouterView />
    </Router>
  );
}
