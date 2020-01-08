import React, { useState } from 'react';
import { Link as RouterLink } from 'react-router-dom';
import AppBar from '@material-ui/core/AppBar';
import Toolbar from '@material-ui/core/Toolbar';
import Button from '@material-ui/core/Button';
import MenuIcon from '@material-ui/icons/Menu';
import Typography from '@material-ui/core/Typography';
import CssBaseline from '@material-ui/core/CssBaseline';
import ChevronLeftIcon from '@material-ui/icons/ChevronLeft';
import ChevronRightIcon from '@material-ui/icons/ChevronRight';
import Divider from '@material-ui/core/Divider';
import Drawer from '@material-ui/core/Drawer';
import List from '@material-ui/core/List';
import ListItem from '@material-ui/core/ListItem';
import ListItemIcon from '@material-ui/core/ListItemIcon';
import ListItemText from '@material-ui/core/ListItemText';
import IconButton from '@material-ui/core/IconButton';
import InboxIcon from '@material-ui/icons/MoveToInbox';
import MailIcon from '@material-ui/icons/Mail';

const MENU_ITEMS = [
  {
    name: 'Faculties',
    link: '/faculties',
  },
];

export default function AppNavBar() {
  const [isOpen, setOpen] = useState(false);

  const handleDrawerClick = () => setOpen(!isOpen);

  return (
    <>
      <AppBar position="static">
        <Toolbar>
          <IconButton edge="start" color="inherit" aria-label="Menu" onClick={handleDrawerClick}>
            <MenuIcon />
          </IconButton>
          <Typography variant="h6">
            <RouterLink to="/">
              React University Application
            </RouterLink>
          </Typography>
          <Typography variant="h6">
            <RouterLink to="/faculties">
              Faculties
            </RouterLink>
          </Typography>
          <Button color="inherit">
            Login
          </Button>
        </Toolbar>
      </AppBar>
      <Drawer variant="persistent" anchor="left" open={isOpen}>
        <div>
          <IconButton onClick={handleDrawerClick}>
            <ChevronLeftIcon />
          </IconButton>
        </div>

        <Divider />

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
