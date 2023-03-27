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
import { json } from 'react-router-dom';


const Mine = ({ token }) => {

  // actual states
  const [profile, setProfile] = React.useState('');
  const [senderNames, setSenderNames] = useState('');
  
  var requestOptions = {
    method: 'GET',
    headers:{
        Authorization: `Token ${token}`
    },
    redirect: 'follow'
  };
  
  fetch("http://127.0.0.1:8000/api/getsendernames", requestOptions)
  .then(response => response.text())
  .then(result => console.log(result))
  .catch(error => console.log('error', error));

    // const result = profile[0]
    // console.log(result)
// // Convert the JSON string to a JavaScript object
// let parsed_response = JSON.parse(profile)[0];

// // Convert the JavaScript object to a formatted JSON string
// let formatted_response = JSON.stringify(parsed_response, null, 2);

// console.log(formatted_response);


  return (
    <div className='profile-container h-100'>
      <div>
        {profile}
        {profile.success}
        {/* {result} */}
        {/* {profile}
      {data.response}
      <ul>
              {data.map(s => (<li>{s}</li>))}
      </ul> */}

      </div>
      <div className='profile-paper' sx={{ height: "100%" }}>

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
                

                {/* Add Modal */}


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
            
          </Paper>
        </div >
        <br />
        <br />
      </div>


      <br />






    </div >



  )
}

export default Mine
