import React, { useState } from 'react';
import { Link as RouterLink } from 'react-router-dom';

import { makeStyles } from '@material-ui/core/styles';

import AppBar from '@material-ui/core/AppBar';
import Toolbar from '@material-ui/core/Toolbar';
import Button from '@material-ui/core/Button';
import Typography from '@material-ui/core/Typography';
import Divider from '@material-ui/core/Divider';
import Drawer from '@material-ui/core/Drawer';
import List from '@material-ui/core/List';
import ListItem from '@material-ui/core/ListItem';
import ListItemIcon from '@material-ui/core/ListItemIcon';
import ListItemText from '@material-ui/core/ListItemText';
import IconButton from '@material-ui/core/IconButton';

import AccountCircle from '@material-ui/icons/AccountCircle';
import MailIcon from '@material-ui/icons/Mail';
import InboxIcon from '@material-ui/icons/MoveToInbox';
import ChevronLeftIcon from '@material-ui/icons/ChevronLeft';
import MenuIcon from '@material-ui/icons/Menu';

const useStyles = makeStyles(theme => ({
  root: {
    flexGrow: 1,
  },
  menuButton: {
    marginRight: theme.spacing(2),
  },
  title: {
    flexGrow: 1,
  },
}));

const MENU_ITEMS = [
  {
    name: 'Faculties',
    link: '/faculties',
  },
];

export default function AppNavBar() {
  const classes = useStyles();

  const [isOpen, setOpen] = useState(false);

  const handleDrawerClick = e => { console.debug(e); setOpen(!isOpen);};

  return (
    <>
      <AppBar position="sticky">
        <Toolbar>
          <IconButton edge="start" className={classes.menuButton} color="inherit" aria-label="Menu" onClick={handleDrawerClick}>
            <MenuIcon />
          </IconButton>
          <Typography variant="h6" className={classes.title}>
            <RouterLink to="/">
              React University Application
            </RouterLink>
          </Typography>
          {/*<Typography variant="h6">
            <RouterLink to="/faculties">
              Faculties
            </RouterLink>
          </Typography>*/}
          <div>
            <IconButton
              aria-label="account of current user"
              aria-controls="menu-appbar"
              aria-haspopup="true"
              color="inherit"
            >
              <AccountCircle />
            </IconButton>
          </div>
          <Button color="inherit">
            Login
          </Button>
        </Toolbar>
      </AppBar>
      <Drawer variant="temporary" anchor="left" open={isOpen} onClose={handleDrawerClick}>
        <List>
          {MENU_ITEMS.map((item, index) => (
            <ListItem button key={item.name}>
              <ListItemIcon>
                {(index % 2 === 0) ? <InboxIcon /> : <MailIcon />}
              </ListItemIcon>
              <ListItemText primary={item.name} />
            </ListItem>
          ))}
        </List>
      </Drawer>
    </>
  );
}
