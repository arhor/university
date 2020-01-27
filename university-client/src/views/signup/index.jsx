import React, { useState } from 'react';
import Button from '@material-ui/core/Button';
import TextField from '@material-ui/core/TextField';
import Typography from '@material-ui/core/Typography';
import service from 'services/AuthService';

const formContainer = {
  display: 'flex',
  flexFlow: 'row wrap'
};

const style = {
  display: 'flex',
  justifyContent: 'center'
};

export default function Signin() {
  const [user, setUser] = useState({
    email: '',
    password: '',
    firstName: '',
    lastName: '',
  });

  const onChange = (e) => setUser({...user, [e.target.name]: e.target.value});

  const signup = () => {
    service.signUp(user).then(data => console.debug(data));
  };

  return (
    <div>
      <Typography variant="h4" style={style}>Add User</Typography>

      <form style={formContainer}>
        <TextField
          type="text"
          placeholder="Email"
          fullWidth
          margin="normal"
          name="email"
          value={user.email}
          onChange={onChange}
        />
        <TextField
          type="password"
          placeholder="password"
          fullWidth
          margin="normal"
          name="password"
          value={user.password}
          onChange={onChange}
        />
        <TextField
          type="text"
          placeholder="First Name"
          fullWidth
          margin="normal"
          name="firstName"
          value={user.firstName}
          onChange={onChange}
        />
        <TextField
          type="text"
          placeholder="Last Name"
          fullWidth
          margin="normal"
          name="lastName"
          value={user.lastName}
          onChange={onChange}
        />
        <Button variant="contained" color="primary" onClick={signup}>
          Sign up
        </Button>
      </form>
    </div>
  );
}