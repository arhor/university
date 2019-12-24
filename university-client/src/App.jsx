import React from 'react';
import AppRouter from "./component/RouterComponent";
import NavBar from "./component/Navbar";
import Container from '@material-ui/core/Container';


export default function App() {
  return (
    <>
      <NavBar/>
      <Container>
        <AppRouter/>
      </Container>
    </>
  );
}
