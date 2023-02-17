// import { FormGroup, Input } from '@mui/material'
import { Tab, Box, TabContext, TabList, TabPanel, TextField, Stack, Card, CardHeader, Paper } from '@mui/material';
import * as React from 'react';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TablePagination from '@mui/material/TablePagination';
import TableRow from '@mui/material/TableRow';
import Modal from '@mui/material/Modal'
// import Box from '@mui/material/Box';
// import Tab from '@mui/material/Tab';
// import TabContext from '@mui/lab/TabContext';
// import TabList from '@mui/lab/TabList';
// import TabPanel from '@mui/lab/TabPanel';


import { Button, Form, Label, FormGroup, Input, CloseButton } from 'reactstrap'
import './MyProfile.css'
import { useState } from 'react';
import { Typography } from '@material-ui/core';

const columns = [

  {
    id: 'name',
    label: 'Name',
    minWidth: 50,
    align: 'left',
    format: (value) => value.toLocaleString('en-US'),
  },
  {
    id: 'expires',
    label: 'Expires',
    minWidth: 30,
    align: 'left',
    format: (value) => value.toLocaleString('en-US'),
  },

];

function createData(name, expires) {
  return { name, expires };
}

const rows = [
  createData('Test', '11 feb 2020'),
  createData('Test', '11 feb 2020'),
  createData('Test', '11 feb 2020'),

];
const MyProfile = () => {
  const [value, setValue] = React.useState('1');
  const [page, setPage] = React.useState(0);
  const [rowsPerPage, setRowsPerPage] = React.useState(10);

  // Modal


  const [open, setOpen] = useState(false)
  const handleOpen = () => setOpen(true)
  const handleClose = () => setOpen(false)

  const handleChange = (event: React.SyntheticEvent, newValue: string) => {
    setValue(newValue);
  };
  return (
    <div className='profile-container'>
      <Paper className='profile-paper'>

        <div className='profile-form m-4' >
          <Typography className='profile-header'>My Profile</Typography>
          <Form>
            <Stack direction="row" gap={1}>
              <FormGroup className='mb-2'>
                <TextField
                  id="outlined-read-only-input"
                  label="Customer No"
                  defaultValue="Benitha"
                  size='small'
                  InputProps={{
                    readOnly: true,
                  }}
                />

              </FormGroup>
              <FormGroup>
                <TextField
                  id="outlined-read-only-input"
                  label="Username"
                  defaultValue="Benitha"
                  size='small'
                  InputProps={{
                    readOnly: true,
                  }}
                />
              </FormGroup>
            </Stack>

            <Stack direction="row" gap={1}>

              <FormGroup>
                <TextField
                  id="outlined-read-only-input"
                  label="Names"
                  defaultValue="Benitha"
                  size='small'
                  InputProps={{
                    readOnly: true,
                  }}
                /></FormGroup>
              <FormGroup>
                <TextField
                  id="outlined-read-only-input"
                  label="Mobile Phone"
                  defaultValue="078766768"
                  size='small'
                  InputProps={{
                    readOnly: true,
                  }}
                /></FormGroup>
            </Stack>
            <Stack direction="row" gap={1}>

              <FormGroup>
                <TextField
                  id="outlined-read-only-input"
                  label="Office Phone"
                  defaultValue="078787879"
                  size='small'
                  InputProps={{
                    readOnly: true,
                  }}
                /></FormGroup>
              <FormGroup>
                <TextField
                  id="outlined-read-only-input"
                  label="Email"
                  defaultValue="benitha@gmail.com"
                  size='small'
                  InputProps={{
                    readOnly: true,
                  }}
                /></FormGroup>
            </Stack>
            <Stack direction="row" gap={1}>

              <FormGroup>
                <TextField
                  id="outlined-read-only-input"
                  label="Company"
                  defaultValue="ISCO"
                  size='small'
                  InputProps={{
                    readOnly: true,
                  }}
                />
              </FormGroup>
              <FormGroup>
                <TextField
                  id="outlined-read-only-input"
                  label="Address"
                  defaultValue="Kigali,Nyarugenge"
                  size='small'
                  InputProps={{
                    readOnly: true,
                  }}
                />
              </FormGroup>
            </Stack>
            <Stack direction="row" gap={1}>

              <FormGroup>
                <TextField
                  id="outlined-read-only-input"
                  label="Account Bal"
                  defaultValue="200"
                  size='small'
                  InputProps={{
                    readOnly: true,
                  }}
                />
              </FormGroup>
              <FormGroup>
                <TextField
                  id="outlined-read-only-input"
                  label="Commission Bal"
                  defaultValue="23900"
                  size='small'
                  InputProps={{
                    readOnly: true,
                  }}
                />
              </FormGroup>
            </Stack>
            <Stack direction="row" gap={2}>
              <FormGroup>
                <TextField
                  id="outlined-read-only-input"
                  label="Auth Token"
                  defaultValue="56utihtgjkfj"
                  size='small'
                  InputProps={{
                    readOnly: true,
                  }}
                />
              </FormGroup>
              <FormGroup>

                <Button className='profile-buttons'>
                  Generate Token
                </Button>
              </FormGroup>
            </Stack>



            <FormGroup>
              <Button className='profile-buttons me-2' onClick={handleOpen}>Change Password</Button>

              <Modal
                open={open}
                onClose={handleClose}
                aria-labelledBy="modal-modal-title"
                aria-describedby='modal-modal-description'

              >
                <Box
                  className='profile-modal'
                  sx={{
                    width: '30%',
                    // height:'5rem',
                    '&:hover': {
                      backgroundColor: 'primary.dark',
                      opacity: [1, 0.8, 1],
                    }
                  }}
                >
                  <div className="modal-title">
                    <Typography>Change Password</Typography>
                  </div>


                  <Stack>
                    <Form className='modal-form'>
                      <FormGroup>
                        <TextField
                          id="outlined-input"
                          label="New Password"
                          size='small'
                          className='modal-input'

                        />
                      </FormGroup>
                      <FormGroup>
                        <TextField
                          id="outlined-input"
                          label="Confirm New Password"
                          size='small'
                          className='modal-input'

                        />
                      </FormGroup>

                      <Stack direction="row" gap={2}>
                        <Button className='profile-buttons'>Save Changes</Button>
                        <Button className='profile-buttons'>Cancel</Button>

                      </Stack>

                    </Form>
                  </Stack>
                </Box>

              </Modal>
              <Button className='profile-buttons'>Save Changes</Button>
            </FormGroup>

          </Form>
        </div>

        <div className='profile-contents'>

          <Paper>
            <div className='profile-header xl-2'>
              <Button className='profile-buttons me-2'> My Sender Names</Button>
              <Button className='profile-buttons me-2' > My Shortcuts </Button>
              <Button className='profile-buttons me-2'> My Keywords</Button>
            </div>
            <hr />
            <TableContainer sx={{ maxHeight: 440 }}>
              <Table stickyHeader aria-label="sticky table">
                <TableHead style={{ fontWeight: '700px' }}>

                  <TableRow>
                    {columns.map((column) => (
                      <TableCell
                        key={column.id}
                        align={column.align}
                        style={{ top: 57, minWidth: column.minWidth }}
                      >
                        {column.label}
                      </TableCell>
                    ))}
                  </TableRow>
                </TableHead>
                <TableBody>
                  {rows
                    .slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage)
                    .map((row) => {
                      return (
                        <TableRow hover role="checkbox" tabIndex={-1} key={row.code}>
                          {columns.map((column) => {
                            const value = row[column.id];
                            return (
                              <TableCell key={column.id} align={column.align}>
                                {column.format && typeof value === 'number'
                                  ? column.format(value)
                                  : value}
                              </TableCell>
                            );
                          })}
                        </TableRow>
                      );
                    })}
                </TableBody>
              </Table>
            </TableContainer>

          </Paper>
        </div >
      </Paper>


      <br />






    </div>



  )
}

export default MyProfile
