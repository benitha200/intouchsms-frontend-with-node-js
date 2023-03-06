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
import { Button, Col, Form, FormGroup, InputGroup, Row } from 'react-bootstrap';
import { useState } from 'react';
import Modal from 'react-bootstrap/Modal';
// import Box from '@mui/material/Box';
// import Tab from '@mui/material/Tab';
// import TabContext from '@mui/lab/TabContext';
// import TabList from '@mui/lab/TabList';
// import TabPanel from '@mui/lab/TabPanel';
import './MyProfile.css';
import { responsiveFontSizes, Typography } from '@material-ui/core';
import { BsKey, BsKeyFill, BsSearch } from 'react-icons/bs';



const columns = [

  {
    id: 'name',
    label: 'Name',
    minWidth: 50,
    align: 'left',
    // format: (value) => value.toLocaleString('en-US'),
  },
  {
    id: 'expires',
    label: 'Expires',
    minWidth: 30,
    align: 'left',
    // format: (value) => value.toLocaleString('en-US'),
  },

];

function createData(name, expires) {
  return { name, expires };
}

const rows = [
  createData('Test1', '11 feb 2021'),
  createData('Test2', '11 feb 2022'),
  createData('Test3', '11 feb 2023'),

];
const MyProfile = ({token}) => {

  // Dummy data
  const [value, setValue] = React.useState('1');
  const [page, setPage] = React.useState(0);
  const [rowsPerPage, setRowsPerPage] = React.useState(10);

  // actual states
  const [profile,setProfile]=React.useState(['']);
  const [senderNames,setSenderNames]=useState('');

  // Modal


  const [show, setShow] = useState(false)

  const handleClose = () => setShow(false)
  const handleShow = () => setShow(true)

  const handleSubmit = (e) => {
    e.preventDefault();
  }

  // get profile

  let requestOptions={
    method:"POST",
    mode:"cors",
    headers:{
      'Authorization':`Token ${token}`
    }
  }

  fetch("http://127.0.0.1:8000/api/getmyprofile",requestOptions)
  .then(response=>response.json())
  .then(result=>setProfile(result[0]))
  .catch(error => console.log('error', error));

  console.log(profile)


  // get sender names

  let senderName_requestOptions={
    method:"GET",
    headers:{
      Authorization:`Token ${token}`
    }
  }

  fetch("http://127.0.0.1:8000/api/getsendernames",senderName_requestOptions)
  .then(response=>response.text())
  .then(result=>console.log(result))
  .catch(error=>console.log('error',error))


  return (
    <div className='profile-container h-100'>
      <div className='profile-paper d-flex flex-row' sx={{height:"100%"}}>

        <div className='profile-form m-4' >
          <Typography className='profile-header'>My Profile</Typography>
          <Form>
            <Stack direction="row" gap={1}>
              <FormGroup className='mb-2'>
                <TextField
                  id="outlined-read-only-input"
                  className='form-text-input'
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

              <FormGroup className='mb-2'>
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

              <FormGroup className='mb-2'>
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

              <FormGroup className='mb-2'>
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

              <FormGroup className='mb-2'>
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

            <Stack direction="row" gap={1}>


              <FormGroup>

                <button className='profile-buttons mt-2'>
                  Generate Token
                </button>
              </FormGroup>




              <FormGroup>
                <Button
                  className='app-buttons mt-0 mr-2 text-dark' onClick={handleShow}
                >Change Password</Button>

                {/* Add Modal */}

                <Modal show={show} onHide={handleClose} className="modal">
                  <Modal.Header className='modal-header' closeButton>
                    <span className='contact-modal-title'><i className='fa fa-key'></i> CHANGE PASSWORD</span>
                  </Modal.Header>
                  <Form >
                  <Modal.Body>
                    

                      {/* <InputGroup className="mb-3">
                        <InputGroup.Text>
                          <BsSearch />
                        </InputGroup.Text>
                        <Form.Control placeholder="Search" aria-label="Search" />
                      </InputGroup> */}

                      <InputGroup className='mb-3'>
                        {/* <Form.Label>New Password</Form.Label> */}
                        <InputGroup.Text>
                          <BsKey />
                        </InputGroup.Text>
                        <Form.Control type='password' placeholder='New Password' required></Form.Control>
                      </InputGroup>

                       <InputGroup>
                       <InputGroup.Text><BsKeyFill/></InputGroup.Text>
                        {/* <Form.Label>Confirm New password</Form.Label> */}
                      <Form.Control type='password' placeholder='Confirm New Password' required></Form.Control>
                       </InputGroup>
                     



                   
                  </Modal.Body>
                  <Modal.Footer>

                    <Button className='btn btn-dark opacity-60' type='submit'>Save Changes</Button>

                    <Button className='btn btn-dark opacity-60' onClick={handleClose}> Cancel</Button>

                  </Modal.Footer> 
                  </Form>

                </Modal>


                {/* end modal */}
                <button className='profile-buttons mt-2'>Save Changes</button>
              </FormGroup>
            </Stack>
          </Form>

        </div>

        <div className='profile-contents'>

          <Paper>
            <div className='profile-header xl-2'>
              <button className='app-buttons me-2'> My Sender Names</button>
              <button className='app-buttons me-2' > My Shortcuts </button>
              <button className='app-buttons me-2'> My Keywords</button>
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
        <br/>
        <br/>
      </div>


      <br />






    </div >



  )
}

export default MyProfile
