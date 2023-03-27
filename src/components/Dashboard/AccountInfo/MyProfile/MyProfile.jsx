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
// import { BsKey, BsKeyFill, BsSearch } from 'react-icons/bs';
import {Link,Outlet} from 'react-router-dom';
import { BsCashCoin, BsCashStack, BsCheck2Square, BsCode, BsEnvelopeFill, BsHouseFill, BsInfoCircle, BsInfoLg, BsKey, BsKeyFill, BsMailbox, BsMap, BsPerson, BsPersonBadgeFill, BsPersonCheck, BsPersonCheckFill, BsPersonFill, BsPersonX, BsPhoneFill, BsPlusCircleFill, BsSortNumericDown, BsTelephoneFill, BsTelephoneInboundFill } from 'react-icons/bs';



const MyProfile = ({ token }) => {



  // actual states
  const [profile, setProfile] = React.useState('');
  const [senderNames, setSenderNames] = useState('');

  // Modal


  const [show, setShow] = useState(false)

  const handleClose = () => setShow(false)
  const handleShow = () => setShow(true)

  const handleSubmit = (e) => {
    e.preventDefault();
  }

  // get profile


  let requestOptions = {
    method: "POST",
    mode: "cors",
    headers: {
      'Authorization': `Token ${token}`
    }
  }

  // React.useEffect(()=>{

  fetch("http://127.0.0.1:8000/api/getmyprofile", requestOptions)
    .then(response => response.json())
    .then(result => setProfile(convertToJson(result)))
    // .then(result => setProfile(result))
    .catch(error => console.log('error', error));

  //  console.log(profile);

  // get sender names

  let senderName_requestOptions = {
    method: "GET",
    headers: {
      'Authorization': `Token ${token}`
    },
    redirect: 'follow'
  }


  fetch("http://127.0.0.1:8000/api/getsendernames", senderName_requestOptions)
    .then(response => response.json())
    .then(result => setSenderNames(convertToJsonList(result)))
    .catch(error => console.log('error', error))

  // console.log(senderNames)




  function convertToJson(response) {
    let json_string = JSON.stringify(response[0]).replace("success", "'success'");
    json_string = json_string.replace("response", "'response'");
    const data = json_string.replace(/'/g, '"');
    const newstr = data.substring(1, data.length - 1)
    console.log(newstr)
    return JSON.parse(newstr);

  }

  // for getting valid json list
  function convertToJsonList(response) {
    let json_string = JSON.stringify(response[0]).replace('success', '"success"');
    // json_string = json_string.replace("msg", "'msg'");
    let data = json_string.startsWith("'");
    data = json_string.endsWith("'");
    data = json_string.substring(1, json_string.length - 1);
    data = data.replace(/\\/g, '');
    console.log(data);
    return JSON.parse(data)
  }

  return (
    <div className='profile-container h-100'>
      <div className='profile-paper' sx={{ height: "100%" }}>


        <div className='profile-form m-3'>
          {profile && (
            <Form >
              <span className="profile-header pt-0"><BsPersonFill/> My profile</span>
              <hr/>

              <div className='d-flex flex-col mb-1 gap-1'>
                <InputGroup>
                  {/* <Form.Label>New Password</Form.Label> */}
                  <InputGroup.Text className="w-25 gap-2">
                    <BsPersonBadgeFill/>
                    Customer No
                  </InputGroup.Text>
                  <Form.Control type='text' className='text-right bg-white' value={profile.response.customerno} readOnly></Form.Control>
                </InputGroup>

              </div>
              <div className='d-flex flex-col mb-1 gap-1'>
                <InputGroup>
                  {/* <Form.Label>New Password</Form.Label> */}
                  <InputGroup.Text className="w-25 gap-2">
                    <BsPersonFill />
                    Username
                  </InputGroup.Text>
                  <Form.Control type='text' className='text-right bg-white' value={profile.response.username} readOnly></Form.Control>
                </InputGroup>

              </div>
              <div className='d-flex flex-row mb-1 gap-2'>
                <InputGroup>
                  {/* <Form.Label>New Password</Form.Label> */}
                  <InputGroup.Text className="w-25 gap-2">
                    <BsPerson/>
                    Names
                  </InputGroup.Text>
                  <Form.Control type='text' className='text-right bg-white' value={profile.response.names} readOnly></Form.Control>
                </InputGroup>
              </div>
              <div className='d-flex flex-row mb-1 gap-2'>
                <InputGroup>
                  {/* <Form.Label>New Password</Form.Label> */}
                  <InputGroup.Text className="w-25 gap-2">
                    <BsTelephoneFill/>
                    Mobile Phone
                  </InputGroup.Text>
                  <Form.Control type='text' className='text-right bg-white' value={profile.response.mobilephone} readOnly></Form.Control>
                </InputGroup>
              </div>
              <div className='d-flex flex-row mb-1 gap-2'>
                <InputGroup>
                  {/* <Form.Label>New Password</Form.Label> */}
                  <InputGroup.Text className="w-25 gap-2">
                    <BsTelephoneInboundFill/>
                    Office Phone
                  </InputGroup.Text>
                  <Form.Control type='text' className='text-right bg-white' value={profile.response.officephone} readOnly></Form.Control>
                </InputGroup>
              </div>

              <div className='d-flex flex-row mb-1 gap-2'>
                <InputGroup>
                  {/* <Form.Label>New Password</Form.Label> */}
                  <InputGroup.Text className="w-25 gap-2">
                    <BsEnvelopeFill/>
                    Email
                  </InputGroup.Text>
                  <Form.Control type='text' className='text-right bg-white' value={profile.response.email} readOnly></Form.Control>
                </InputGroup>
              </div>
              <div className='d-flex flex-row mb-1 gap-2'>
                <InputGroup>
                  {/* <Form.Label>New Password</Form.Label> */}
                  <InputGroup.Text className="w-25 gap-2">
                    <BsHouseFill/>
                    Company
                  </InputGroup.Text>
                  <Form.Control type='text' className='text-right bg-white' value={profile.response.company} readOnly></Form.Control>
                </InputGroup>
              </div>
              <div className='d-flex flex-row mb-1 gap-2'>
                <InputGroup>
                  {/* <Form.Label>New Password</Form.Label> */}
                  <InputGroup.Text className="w-25 gap-2">
                    <BsMap/>
                    Address
                  </InputGroup.Text>
                  <Form.Control type='text' className='text-right bg-white' value={profile.response.address} readOnly></Form.Control>
                </InputGroup>
              </div>
              <div className='d-flex flex-row mb-1 gap-2'>
                <InputGroup>
                  {/* <Form.Label>New Password</Form.Label> */}
                  <InputGroup.Text className="w-25 gap-2">
                    <BsCashCoin/>
                    Account Bal
                  </InputGroup.Text>
                  <Form.Control type='text' className='text-right bg-white' value={profile.response.creditsbalance} readOnly></Form.Control>
                </InputGroup>
              </div>

              <div className='d-flex flex-row mb-1 gap-2'>
                <InputGroup>
                  {/* <Form.Label>New Password</Form.Label> */}
                  <InputGroup.Text className="w-25 gap-2">
                    <BsCashStack/>
                    Commission bal
                  </InputGroup.Text>
                  <Form.Control type='text' className='text-right bg-white' value={profile.response.commissionbalance} readOnly></Form.Control>
                </InputGroup>
              </div>
              <div className='d-flex flex-row mb-1 gap-2'>
                <InputGroup>
                  {/* <Form.Label>New Password</Form.Label> */}
                  <InputGroup.Text className="w-25 gap-2">
                    <BsKeyFill/>
                    Auth Token
                  </InputGroup.Text>
                  <Form.Control type='text' className='text-right bg-white' value={profile.response.authtoken}></Form.Control>
                </InputGroup>
              </div>

              <div className='d-flex flex-row mb-1 gap2'>
                <button className='profile-buttons mt-2'> Generate Token </button>

                <Button className='profile-buttons mt-1 mr-1 text-dark text-sm' onClick={handleShow}>Change Password</Button>

                <button type='submit' className='profile-buttons mt-1'>Save Changes</button>

              </div>

              {/* {/* Add Modal */}

              <Modal show={show} onHide={handleClose} className="modal">
                <Modal.Header className='modal-header' closeButton>
                  <span className='contact-modal-title'><i className='fa fa-key'></i> CHANGE PASSWORD</span>
                </Modal.Header>
                <Form >
                  <Modal.Body>

                    <InputGroup className='mb-3'>
                      {/* {/* <Form.Label>New Password</Form.Label> */}
                      <InputGroup.Text>
                        <BsKey />
                      </InputGroup.Text>
                      <Form.Control type='password' placeholder='New Password' required></Form.Control>
                    </InputGroup>

                    <InputGroup>
                      <InputGroup.Text><BsKeyFill /></InputGroup.Text>
                      {/* {/* <Form.Label>Confirm New password</Form.Label> */}
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


            </Form>
          )}


        </div>
        {/* 
        <div className='profile-form m-4' >
          <Typography className='profile-header'>My Profile</Typography>
          
          <Form>
          {profile && (
            <>
            <Stack direction="row" gap={1}>
           
                <TextField
                  id="outlined-read-only-input"
                  className='form-text-input'
                  label="Customer No"
                  value={profile.response.customerno}
                  size='small'
                  InputProps={{
                    readOnly: true,
                  }}
                />

             
                <TextField
                  id="outlined-read-only-input"
                  label="Username"
                  value={profile.response.username}
                  size='small'
                  InputProps={{
                    readOnly: true,
                  }}
                />
          
            </Stack>

            <Stack direction="row" gap={1}>

                <TextField
                  id="outlined-read-only-input"
                  label="Names"
                  value={profile.response.names}
                  size='small'
                  InputProps={{
                    readOnly: true,
                  }}
                />
              
                <TextField
                  id="outlined-read-only-input"
                  label="Mobile Phone"
                  value={profile.response.mobilephone}
                  size='small'
                  InputProps={{
                    readOnly: true,
                  }}
                />
            </Stack>
            <Stack direction="row" gap={1}>

                <TextField
                  id="outlined-read-only-input"
                  label="Office Phone"
                  value={profile.response.officephone}
                  size='small'
                  InputProps={{
                    readOnly: true,
                  }}
                />
                <TextField
                  id="outlined-read-only-input"
                  label="Email"
                  value={profile.response.email}
                  size='small'
                  InputProps={{
                    readOnly: true,
                  }}
                />
            </Stack>
            <Stack direction="row" gap={1}>

                <TextField
                  id="outlined-read-only-input"
                  label="Company"
                  value={profile.response.company}
                  size='small'
                  InputProps={{
                    readOnly: true,
                  }}
                />
              
                <TextField
                  id="outlined-read-only-input"
                  label="Address"
                  value={profile.response.address}
                  size='small'
                  InputProps={{
                    readOnly: true,
                  }}
                />
             
            </Stack>
            <Stack direction="row" gap={1}>

             
                <TextField
                  id="outlined-read-only-input"
                  label="Account Bal" 
                  value={profile.response.creditsbalance}
                  size='small'
                  InputProps={{
                    readOnly: true,
                  }}
                />
             
            
                <TextField
                  id="outlined-read-only-input"
                  label="Commission Bal"
                  value={profile.response.commissionbalance}
                  size='small'
                  InputProps={{
                    readOnly: true,
                  }}
                />
              
            </Stack>
            <Stack direction="column">
              <TextField
                id="outlined-read-only-input"
                label="Auth Token"
                value={profile.response.authtoken}
                size='small'
                InputProps={{
                  readOnly: true,
                }}
              />
            </Stack>
            </>
             )}

            <Stack direction="row" gap={1}>
      
           

                <button className='profile-buttons mt-2'>
                  Generate Token
                </button>
            




          
                <Button
                  className='app-buttons mt-0 mr-2 text-dark' onClick={handleShow}
                >Change Password</Button>

                {/* Add Modal

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
                      </InputGroup> 

                      <InputGroup className='mb-3'>
                        {/* <Form.Label>New Password</Form.Label>
                        <InputGroup.Text>
                          <BsKey />
                        </InputGroup.Text>
                        <Form.Control type='password' placeholder='New Password' required></Form.Control>
                      </InputGroup>

                      <InputGroup>
                        <InputGroup.Text><BsKeyFill /></InputGroup.Text>
                        {/* <Form.Label>Confirm New password</Form.Label>
                        <Form.Control type='password' placeholder='Confirm New Password' required></Form.Control>
                      </InputGroup>





                    </Modal.Body>
                    <Modal.Footer>

                      <Button className='btn btn-dark opacity-60' type='submit'>Save Changes</Button>

                      <Button className='btn btn-dark opacity-60' onClick={handleClose}> Cancel</Button>

                    </Modal.Footer>
                  </Form>

                </Modal>


                {/* end modal 
                <button className='profile-buttons mt-2'>Save Changes</button>
              
            </Stack>
          </Form>

        </div> */}

        <div className='profile-contents'>

          <Paper sx={{height:'100%'}}>
            {/* <div className='profile-headers xl-2'>
              <button className='profile-header-buttons me-2'> My Sender Names</button>
              <button className='profile-header-buttons me-2' > My Shortcuts </button>
              <button className='profile-header-buttons me-2'> My Keywords</button>
            </div> */}

            <div className='pt-0'>
              <ul className="nav nav-tabs pt-0">
                <li className="nav-item">
                  <Link to="/account-info/" className="nav-link active">My Sender Names</Link>
                </li>
                <li className="nav-item profile-nav-items">
                  <Link to="/account-info/my-clients/" className="nav-link active">My Shortcut</Link>
                </li>
                <li className="nav-item">
                  <Link to="/account-info/my-credit-transfers/" className="nav-link active">My Keywords</Link>
                </li>
              
              </ul>
              <Outlet />
            </div>
            <hr />
            {/* <TableContainer sx={{ maxHeight: 440,border:"2px" }}>
              <Table stickyHeader aria-label="sticky table">
                <TableHead style={{ fontWeight: '700px' }}>

                  <TableRow>
                    {columns.map((column) => (
                      <TableCell
                        key={column.id}
                        align={column.align}
                        style={{ top: 2, minWidth: column.minWidth }}
                      >
                        {column.label}
                      </TableCell>
                    ))}
                  </TableRow>
                </TableHead>
                <TableBody>
                  {senderNames && senderNames.response.map((sendername) =>(
                    <tr key={sendername.fields.sender.pk}>
                      <td>{sendername.fields.sender.fields.sendername}</td>

                    </tr>
                  ))}
                  <tr></tr>
                </TableBody>
              </Table>
            </TableContainer> */}

            <table className='table table-hover w-100 pt-0' style={{ height: '100%', fontFamily: 'verdana', fontWeight: 'bold' }}>
              <thead className='table-headers'>
                <tr>
                  <th>Sender Name</th>
                  <th>Expires</th>
                </tr>
              </thead>

              <tbody>

                {senderNames && senderNames.response.map((sendername) => (
                  <tr key={sendername.fields.sender.pk}>
                    <td className="table-data">{sendername.fields.sender.fields.sendername}</td>
                    <td>{sendername.fields.expires}</td>

                  </tr>
                ))}

              </tbody>

            </table>
          </Paper>
        </div >
        <br />
        <br />
      </div>


      <br />






    </div >



  )
}

export default MyProfile
