// import { FormGroup, Input } from '@mui/material'
import { Paper } from '@mui/material';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TablePagination from '@mui/material/TablePagination';
import TableRow from '@mui/material/TableRow';
import { Button,Form,InputGroup } from 'react-bootstrap';
import { useState } from 'react';
import Modal from 'react-bootstrap/Modal';
// import Box from '@mui/material/Box';
// import Tab from '@mui/material/Tab';
// import TabContext from '@mui/lab/TabContext';
// import TabList from '@mui/lab/TabList';
// import TabPanel from '@mui/lab/TabPanel';
import './MyProfile.css';
// import { BsKey, BsKeyFill, BsSearch } from 'react-icons/bs';
import { Link, Outlet } from 'react-router-dom';
import { BsCashCoin, BsCashStack, BsEnvelopeFill, BsHouseFill,BsKey, BsKeyFill, BsMap, BsPerson, BsPersonBadgeFill,  BsPersonFill, BsTelephoneFill, BsTelephoneInboundFill } from 'react-icons/bs';
import { API_URL } from '../../../../Constants/Index';

const columns = [

  {
    id: 'sendername',
    label: 'Sender Names',
    minWidth: 100,
    align: 'left',
    format: (value) => value.toLocaleString('en-US'),
  },
  {
    id: 'expires',
    label: 'Expires',
    minWidth: 50,
    align: 'left',
    format: (value) => value.toLocaleString('en-US'),
  },



];
const MyProfile = ({ token }) => {

  const [page, setPage] = useState(0);
  const [rowsPerPage, setRowsPerPage] = useState(10);

  // actual states
  const [profile, setProfile] = useState('');
  const [senderNames, setSenderNames] = useState('');

  // change password

  const [newpassword, setNewpassword] = useState('')
  const [confirmpass, setConfirmpass] = useState('')
  const [message, setMessage] = useState('')

  // Modal


  const [show, setShow] = useState(false)

  const handleClose = () => setShow(false)
  const handleShow = () => setShow(true)



  // get profile


  let requestOptions = {
    method: "POST",
    mode: "cors",
    headers: {
      'Authorization': `Token ${token}`
    }
  }

  // React.useEffect(()=>{

  fetch(API_URL + "getmyprofile", requestOptions)
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


  fetch(API_URL + "getsendernames", senderName_requestOptions)
    .then(response => response.json())
    .then(result => setSenderNames(convertToJsonList(result)))
    .catch(error => console.log('error', error))

  // console.log(senderNames)


  const handleChangePage = (event, newPage) => {
    setPage(newPage);
  };

  const handleChangeRowsPerPage = (event) => {
    setRowsPerPage(+event.target.value);
    setPage(0);
  };

  function changePassword(e) {
    e.preventDefault();
    var formdata = new FormData();
    if (newpassword !== confirmpass) {
      setMessage("Passwords doesn't match")
      window.location.reload(false)
    }
    else {

      formdata.append("new_pass", newpassword);

      var requestOptions = {
        method: 'POST',
        headers: {
          'Authorization': `Token ${token}`,
        },
        body: formdata,
      };

      fetch(API_URL + "changepassword", requestOptions)
        .then(response => response.text())
        .then(result => setMessage(convertToJson(result).msg))
        .catch(error => console.log('error', error));
      window.location.reload(false)

    }
  }


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
              <span className="profile-header pt-0"><BsPersonFill /> My profile</span>
              <hr />

              <div className='d-flex flex-col mb-1 gap-1'>
                <InputGroup>
                  {/* <Form.Label>New Password</Form.Label> */}
                  <InputGroup.Text className="w-25 gap-2">
                    <BsPersonBadgeFill />
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
                    <BsPerson />
                    Names
                  </InputGroup.Text>
                  <Form.Control type='text' className='text-right bg-white' value={profile.response.names} readOnly></Form.Control>
                </InputGroup>
              </div>
              <div className='d-flex flex-row mb-1 gap-2'>
                <InputGroup>
                  {/* <Form.Label>New Password</Form.Label> */}
                  <InputGroup.Text className="w-25 gap-2">
                    <BsTelephoneFill />
                    Mobile Phone
                  </InputGroup.Text>
                  <Form.Control type='text' className='text-right bg-white' value={profile.response.mobilephone} readOnly></Form.Control>
                </InputGroup>
              </div>
              <div className='d-flex flex-row mb-1 gap-2'>
                <InputGroup>
                  {/* <Form.Label>New Password</Form.Label> */}
                  <InputGroup.Text className="w-25 gap-2">
                    <BsTelephoneInboundFill />
                    Office Phone
                  </InputGroup.Text>
                  <Form.Control type='text' className='text-right bg-white' value={profile.response.officephone} readOnly></Form.Control>
                </InputGroup>
              </div>

              <div className='d-flex flex-row mb-1 gap-2'>
                <InputGroup>
                  {/* <Form.Label>New Password</Form.Label> */}
                  <InputGroup.Text className="w-25 gap-2">
                    <BsEnvelopeFill />
                    Email
                  </InputGroup.Text>
                  <Form.Control type='text' className='text-right bg-white' value={profile.response.email} readOnly></Form.Control>
                </InputGroup>
              </div>
              <div className='d-flex flex-row mb-1 gap-2'>
                <InputGroup>
                  {/* <Form.Label>New Password</Form.Label> */}
                  <InputGroup.Text className="w-25 gap-2">
                    <BsHouseFill />
                    Company
                  </InputGroup.Text>
                  <Form.Control type='text' className='text-right bg-white' value={profile.response.company} readOnly></Form.Control>
                </InputGroup>
              </div>
              <div className='d-flex flex-row mb-1 gap-2'>
                <InputGroup>
                  {/* <Form.Label>New Password</Form.Label> */}
                  <InputGroup.Text className="w-25 gap-2">
                    <BsMap />
                    Address
                  </InputGroup.Text>
                  <Form.Control type='text' className='text-right bg-white' value={profile.response.address} readOnly></Form.Control>
                </InputGroup>
              </div>
              <div className='d-flex flex-row mb-1 gap-2'>
                <InputGroup>
                  {/* <Form.Label>New Password</Form.Label> */}
                  <InputGroup.Text className="w-25 gap-2">
                    <BsCashCoin />
                    Account Bal
                  </InputGroup.Text>
                  <Form.Control type='text' className='text-right bg-white' value={profile.response.creditsbalance} readOnly></Form.Control>
                </InputGroup>
              </div>

              <div className='d-flex flex-row mb-1 gap-2'>
                <InputGroup>
                  {/* <Form.Label>New Password</Form.Label> */}
                  <InputGroup.Text className="w-25 gap-2">
                    <BsCashStack />
                    Commission bal
                  </InputGroup.Text>
                  <Form.Control type='text' className='text-right bg-white' value={profile.response.commissionbalance} readOnly></Form.Control>
                </InputGroup>
              </div>
              <div className='d-flex flex-row mb-1 gap-2'>
                <InputGroup>
                  {/* <Form.Label>New Password</Form.Label> */}
                  <InputGroup.Text className="w-25 gap-2">
                    <BsKeyFill />
                    Auth Token
                  </InputGroup.Text>
                  <Form.Control type='text' className='text-right bg-white' value={profile.response.authtoken} readOnly></Form.Control>
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
                <form encType='multipart/form-data' onSubmit={changePassword} >
                  <Modal.Body>

                    <InputGroup className='mb-3'>
                      {/* {/* <Form.Label>New Password</Form.Label> */}
                      <InputGroup.Text>
                        <BsKey />
                      </InputGroup.Text>
                      <Form.Control
                        type='password'
                        placeholder='New Password'
                        onChange={(e) => setNewpassword(e.target.value)}
                        value={newpassword}
                        required />


                    </InputGroup>

                    <InputGroup>
                      <InputGroup.Text><BsKeyFill /></InputGroup.Text>
                      {/* {/* <Form.Label>Confirm New password</Form.Label> */}
                      <Form.Control
                        type='password'
                        placeholder='Confirm New Password'
                        onChange={(e) => setConfirmpass(e.target.value)}
                        value={confirmpass}
                        required />
                    </InputGroup>

                    {message && (<span className="text-danger mt-2"><b><i>{message}</i></b></span>)}





                  </Modal.Body>
                  <Modal.Footer>

                    <button className='btn btn-dark opacity-60' type='submit'>Save Changes</button>

                    <button className='btn btn-dark opacity-60' onClick={handleClose}> Cancel</button>

                  </Modal.Footer>
                </form>

              </Modal>

              {/* end modal */}


            </Form>
          )}


        </div>


        <div className='profile-contents'>

          <Paper sx={{ height: '100%' }}>
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


            <TableContainer sx={{ maxHeight: 440 }}>
              <Table stickyHeader aria-label="sticky table">
                <TableHead className='my-clients-table'>

                  <TableRow>
                    {columns.map((column) => (
                      <TableCell
                        key={column.id}
                        align={column.align}
                        style={{ top: 0, minWidth: column.minWidth, backgroundColor: "lightgrey" }}
                      >
                        {column.label}
                      </TableCell>
                    ))}
                  </TableRow>
                </TableHead>
                <TableBody>
                  {senderNames && senderNames.response
                    .slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage)
                    .map((sendername) => {
                      return (
                        <TableRow hover role="checkbox" tabIndex={-1} key={sendername.fields.sender.pk}>
                          <TableCell>{sendername.fields.sender.fields.sendername}</TableCell>
                          <TableCell>{sendername.fields.expires}</TableCell>



                        </TableRow>
                      )
                    })}
                </TableBody>
              </Table>
            </TableContainer>
            <TablePagination
              rowsPerPageOptions={[10, 25, 100]}
              component="div"
              count={10}
              rowsPerPage={rowsPerPage}
              page={page}
              onPageChange={handleChangePage}
              onRowsPerPageChange={handleChangeRowsPerPage}
            />
            {/* 
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

            </table> */}
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
