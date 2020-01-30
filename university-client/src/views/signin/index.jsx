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
  });

  const onChange = (e) => setUser({...user, [e.target.name]: e.target.value});

  const login = async () => {
    try {
      const data = await service.signIn(user);
      console.debug(data);
    } catch (err) {
      console.error(err);
    }
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

        <Button variant="contained" color="primary" onClick={login}>
          Save
        </Button>
      </form>
    </div>
  );
}