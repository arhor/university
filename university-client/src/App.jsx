import React from 'react';
import RouterView from './component/RouterView';
import NavBar from './component/Navbar';
import Container from '@material-ui/core/Container';


export default function App() {
  return (
    <>
      <NavBar />
      <Container>
        <RouterView />
      </Container>
    </>
  );
}
