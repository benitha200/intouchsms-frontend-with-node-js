import * as React from 'react';
import Paper from '@mui/material/Paper';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TablePagination from '@mui/material/TablePagination';
import TableRow from '@mui/material/TableRow';
import { Stack } from '@mui/system';
// import { Box, FormGroup, TextField, Typography } from '@mui/material';
// import Modal from '@mui/material/Modal'
import './MyCreditTransfers.css'
import { DataGrid } from '@mui/x-data-grid'
import { Button, Col, Form, FormGroup, InputGroup, Row } from 'react-bootstrap';
import { useState } from 'react';
import Modal from 'react-bootstrap/Modal';
import { BsArrowLeftRight, BsCash, BsCashCoin, BsEnvelopeFill, BsHouseDoorFill, BsInfo, BsInfoCircle, BsPersonCircle, BsPersonFill, BsTelephone } from 'react-icons/bs';


const columns = [

    {
        field: 'transferNo',
        headerName: 'Transfer No',
        // minWidth: 170,
        // align: 'right',
        format: (value) => value.toLocaleString('en-US'),
    },
    {
        field: 'customerNumber',
        headerName: 'Customer No',
        minWidth: 150,
        // align: 'right',
        format: (value) => value.toLocaleString('en-US'),
    },
    {
        field: 'customerName',
        headerName: 'Customer Name',
        minWidth: 170,
        // align: 'right',
        format: (value) => value.toLocaleString('en-US'),
    },
    {
        field: 'phone',
        headerName: 'Phone',
        // minWidth: 170,
        // align: 'right',
        format: (value) => value.toLocaleString('en-US'),
    },
    {
        field: 'email',
        headerName: 'Email',
        width: 170,
        // align: 'right',
        format: (value) => value.toLocaleString('en-US'),
    },
    {
        field: 'amountTransfered',
        headerName: 'Amount Transfered',
        width: 130,
        align: 'center',
        format: (value) => value.toLocaleString('en-US'),
    },
    {
        field: 'created',
        headerName: 'Created',
        // minWidth: 170,
        align: 'right',
        format: (value) => value.toLocaleString('en-US'),
    },
];

function createData(id, transferNo, customerNumber, customerName, phone, email, amountTransfered, created) {
    return { id, transferNo, customerNumber, customerName, phone, email, amountTransfered, created };
}

const rows = [
    createData(1, '0000006758', '235679875', 'ISCO', '0785467789', 'isco@isco.co.rw', 5000, 34567890),
    createData(2, '0000006758', '235679875', 'ISCO', '0785467789', 'isco@isco.co.rw', 5000, 34567890),
    createData(3, '0998435934', '894348759', 'DMC Hospital', '0786743783', 'dmc@dmc.co.rw', 5600, 89743403),


];

const styles = {
    root: {
        fontFamily: '"Helvetica Neue", Helvetica, Roboto, Arial, sans-serif',
        fontSize: '10px',
        fontWeight: 'normal',
    },
};
export default function MyCreditTransfers() {
    const [page, setPage] = React.useState(0);
    const [rowsPerPage, setRowsPerPage] = React.useState(10);
    // const [open, setOpen] = React.useState(false)
    // const handleOpen = () => setOpen(true)
    // const handleClose = () => setOpen(false)


    //modal
    const [show, setShow] = useState(false)
    const handleClose = () => setShow(false)
    const handleShow = () => setShow(true)

    //form

    const handleChangePage = (event, newPage) => {
        setPage(newPage);
    };

    // table

    const handleChangeRowsPerPage = (event) => {
        setRowsPerPage(+event.target.value);
        setPage(0);
    };



    return (
        <Paper sx={{ width: '98%', margin: '0.5rem', overflowX: 'auto' }}>

            <Stack spacing={2} direction="row" sx={{ margin: "1rem" }}>

                {/* <Button variant="outlined" onClick={setOpen} className='app-buttons text-dark m-0'>Add My Credit Transfer</Button> */}
<Button onClick={setShow} className='app-buttons text-dark m-0'>Add My Credit Transfer</Button>

                {/* Add Modal */}

                <Modal show={show} onHide={handleClose} className="modal">
                    <Modal.Header className='modal-header' closeButton>
                        <span className='contact-modal-title'><i className='fa fa-key'></i> ADD CREDIT TRANSFER</span>
                    </Modal.Header>
                     <Form >
                    <Modal.Body>
                       

                            {/* <InputGroup className="mb-3">
                        <InputGroup.Text>
                          <BsSearch />
                        </InputGroup.Text>
                        <Form.Control placeholder="Search" aria-label="Search" />
                      </InputGroup> */} 
                      <span>Credit Transfer Info</span>
                      <hr/>
                      <div className='d-flex flex-row gap-2 mb-3'>
                        <InputGroup>
                                {/* <Form.Label>New Password</Form.Label> */}
                                <InputGroup.Text>
                                    <BsArrowLeftRight/>
                                </InputGroup.Text>
                                <Form.Control type='text' placeholder='Transfer No' required></Form.Control>
                            </InputGroup>

                            <InputGroup>
                                <InputGroup.Text><BsInfo/></InputGroup.Text>
                                {/* <Form.Label>Confirm New password</Form.Label> */}
                                <Form.Control type='textarea' placeholder='Description' required></Form.Control>
                            </InputGroup>
                        </div>
                        <div className='d-flex flex-row gap-2 mb-3'>
                        <InputGroup>
                                {/* <Form.Label>New Password</Form.Label> */}
                                <InputGroup.Text>
                                    <BsCash/>
                                </InputGroup.Text>
                                <Form.Control type='text' placeholder='Account Balance' required></Form.Control>
                            </InputGroup>

                            <InputGroup>
                                <InputGroup.Text><BsCashCoin/></InputGroup.Text>
                                {/* <Form.Label>Confirm New password</Form.Label> */}
                                <Form.Control type='textarea' placeholder='Transfer Amount' required></Form.Control>
                            </InputGroup>
                        </div>

                        <span className="">Credit Transfer Info</span>
                        <hr/>

                        <div className='d-flex flex-row gap-2 mb-3'>
                        <InputGroup>
                                {/* <Form.Label>New Password</Form.Label> */}
                                <InputGroup.Text>
                                    <BsPersonCircle/>
                                </InputGroup.Text>
                                <Form.Control className='m-0 h-100' type='text' placeholder='Customer No' required></Form.Control>
                            </InputGroup>

                            <InputGroup>
                                <Button className='btn btn-dark opacity-50 m-0 h-100 w-100'>Select</Button>
                            </InputGroup>
                        </div>
                        <div className='d-flex flex-row gap-2 mb-3'>
                        <InputGroup>
                                {/* <Form.Label>New Password</Form.Label> */}
                                <InputGroup.Text>
                                  <BsPersonFill/> 
                                </InputGroup.Text>
                                <Form.Control type='text' placeholder='Customer name' required></Form.Control>
                            </InputGroup>

                            <InputGroup>
                                <InputGroup.Text><BsTelephone/></InputGroup.Text>
                                {/* <Form.Label>Confirm New password</Form.Label> */}
                                <Form.Control type='text' placeholder='Phone' required></Form.Control>
                            </InputGroup>
                        </div>
                        <div className='d-flex flex-row gap-2 mb-3'>
                        <InputGroup>
                                {/* <Form.Label>New Password</Form.Label> */}
                                <InputGroup.Text>
                                    <BsEnvelopeFill/>
                                </InputGroup.Text>
                                <Form.Control type='text' placeholder='Email' required></Form.Control>
                            </InputGroup>
                                
                            <InputGroup>
                                <InputGroup.Text><BsHouseDoorFill/> </InputGroup.Text>
                                {/* <Form.Label>Confirm New password</Form.Label> */}
                                <Form.Control type='text' placeholder='Address' required></Form.Control>
                            </InputGroup>
                        </div>

                            




                       
                    </Modal.Body>
                    <Modal.Footer>

                        <Button className='btn btn-dark opacity-60' type='submit'>Save Changes</Button>

                        <Button className='btn btn-dark opacity-60' onClick={handleClose}> Cancel</Button>

                    </Modal.Footer>
                    </Form>

                </Modal>


                {/* end modal */}


                {/* <Modal
                    keepMounted
                    open={open}
                    onClose={handleClose}
                    aria-labelledby="keep-mounted-modal-title"
                    aria-describedby="keep-mounted-modal-body"
                >
                    <Box className="credit-transfer-modal">
                        <div className='credit-transfer-modal-title'>
                            Add My Credit Transfer
                        </div>
                        <Stack>
                            <Box className='credit-transfer-modal-box'>
                                <div className='credit-transfer-modal-box-title'>
                                    <Typography className='credit-transfer-modal-box-title'>Credit Transfer Info</Typography>
                                </div>
                                <Form>
                                    <Stack direction="row" gap={2}>
                                        <FormGroup>
                                            <TextField
                                                id="outline-read-only-input"
                                                label="Transfer No."
                                                defaultValue="(New)..."
                                                size='small'
                                                InputProps={{
                                                    readOnly: true,
                                                }}
                                            />
                                        </FormGroup>
                                        <FormGroup>
                                            <TextField
                                                id="outlined-read-only-input"
                                                label="Description"
                                                defaultValue=""
                                                size='small'
                                                multiline
                                                rows={3}

                                            />
                                        </FormGroup>
                                    </Stack>

                                    <Stack direction="row" gap={2}>
                                        <FormGroup>
                                            <TextField
                                                id="outline-read-only-input"
                                                label="Account Balance"
                                                size='small'
                                                InputProps={{
                                                    readOnly: true,
                                                }}
                                            />
                                        </FormGroup>
                                        <FormGroup>
                                            <TextField
                                                id="outlined-read-only-input"
                                                label="Transfer Amount"
                                                defaultValue=""
                                                size='small'

                                            />
                                        </FormGroup>
                                    </Stack>



                                </Form>
                            </Box>

                            <Box className='credit-transfer-modal-box'>
                                <div className='credit-transfer-modal-box-title'>
                                    <Typography>Credit Transfer Info</Typography>
                                </div>
                                <Form>
                                    <Stack direction="row" gap={2}>
                                        <FormGroup>
                                            <TextField
                                                id="outline-read-only-input"
                                                label="Customer No."
                                                size='small'
                                                InputProps={{
                                                    readOnly: true,
                                                }}
                                            />
                                        </FormGroup>
                                        <FormGroup>
                                            <Button className='credit-transfer-modal-form-buttons'>Select</Button>
                                        </FormGroup>
                                    </Stack>

                                    <Stack direction="row" gap={2}>
                                        <FormGroup>
                                            <TextField
                                                id="outline-read-only-input"
                                                label="Customer Name"
                                                size='small'

                                            />
                                        </FormGroup>
                                        <FormGroup>
                                            <TextField
                                                id="outlined-read-only-input"
                                                label="Phone"
                                                defaultValue=""
                                                size='small'

                                            />
                                        </FormGroup>
                                    </Stack>

                                    <Stack direction="row" gap={2}>
                                        <FormGroup>
                                            <TextField
                                                id="outline-read-only-input"
                                                label="Email"
                                                size='small'

                                            />
                                        </FormGroup>
                                        <FormGroup>
                                            <TextField
                                                id="outlined-read-only-input"
                                                label="Address"
                                                defaultValue=""
                                                size='small'

                                            />
                                        </FormGroup>
                                    </Stack>
                                    <Stack>
                                        <FormGroup>
                                            <TextField
                                                id="outline-read-only-input"
                                                label="Customer Balance"
                                                size='small'
                                                InputProps={{
                                                    readOnly: true,
                                                }}

                                            />
                                        </FormGroup>

                                    </Stack>

                                    <Stack direction="row" gap={2}>
                                        <button className='app-buttons'>Submit</button>
                                        <button className='app-buttons'>Cancel</button>
                                    </Stack>

                                </Form>
                            </Box>

                        </Stack>
                    </Box>


                </Modal> */}
                <button className='app-buttons' variant="contained" color='dark'>View & Modify</button>
                <button className='app-buttons' variant="contained" color='dark'>Refresh</button>
            </Stack>

            <div className='in-between'>

            </div>

            {/* <div class="container">
  <div className="row mt-5">
      <div className="col-md-12">
      <table id="example" className="table table-striped" style={{width:"100%"}}>
    <thead>
      <tr>
        <th>Name</th>
        <th>Position</th>
        <th>Office</th>
        <th>Age</th>
        <th>Start date</th>
        <th>Salary</th>
      </tr>
    </thead>
    <tbody>
      <tr>
        <td>Tiger Nixon</td>
        <td>System Architect</td>
        <td>Edinburgh</td>
        <td>61</td>
        <td>2011/04/25</td>
        <td>$320,800</td>
      </tr>
      <tr>
        <td>Garrett Winters</td>
        <td>Accountant</td>
        <td>Tokyo</td>
        <td>63</td>
        <td>2011/07/25</td>
        <td>$170,750</td>
      </tr>
      <tr>
        <td>Ashton Cox</td>
        <td>Junior Technical Author</td>
        <td>San Francisco</td>
        <td>66</td>
        <td>2009/01/12</td>
        <td>$86,000</td>
      </tr>
      <tr>
        <td>Cedric Kelly</td>
        <td>Senior Javascript Developer</td>
        <td>Edinburgh</td>
        <td>22</td>
        <td>2012/03/29</td>
        <td>$433,060</td>
      </tr>
      <tr>
        <td>Airi Satou</td>
        <td>Accountant</td>
        <td>Tokyo</td>
        <td>33</td>
        <td>2008/11/28</td>
        <td>$162,700</td>
      </tr>
      <tr>
        <td>Brielle Williamson</td>
        <td>Integration Specialist</td>
        <td>New York</td>
        <td>61</td>
        <td>2012/12/02</td>
        <td>$372,000</td>
      </tr>
      <tr>
        <td>Herrod Chandler</td>
        <td>Sales Assistant</td>
        <td>San Francisco</td>
        <td>59</td>
        <td>2012/08/06</td>
        <td>$137,500</td>
      </tr>
      <tr>
        <td>Rhona Davidson</td>
        <td>Integration Specialist</td>
        <td>Tokyo</td>
        <td>55</td>
        <td>2010/10/14</td>
        <td>$327,900</td>
      </tr>
      <tr>
        <td>Colleen Hurst</td>
        <td>Javascript Developer</td>
        <td>San Francisco</td>
        <td>39</td>
        <td>2009/09/15</td>
        <td>$205,500</td>
      </tr>
      <tr>
        <td>Sonya Frost</td>
        <td>Software Engineer</td>
        <td>Edinburgh</td>
        <td>23</td>
        <td>2008/12/13</td>
        <td>$103,600</td>
      </tr>
      <tr>
        <td>Donna Snider</td>
        <td>Customer Support</td>
        <td>New York</td>
        <td>27</td>
        <td>2011/01/25</td>
        <td>$112,000</td>
      </tr>
    </tbody>
   
  </table>
      </div>
    </div>
</div> */}

    <div style={{ height: 400, width: '100%' }}>
        <DataGrid
            rows={rows}
            columns={columns}
            pageSize={5}
            rowsPerPageOptions={[5]}
            checkboxSelection
            className='mui-data-grid'
            style={styles.root}
        />
    </div>

    {/* <TableContainer sx={{ maxHeight: 440 }}>
                <Table stickyHeader aria-label="sticky table">
                    <TableHead>

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
            <TablePagination
                rowsPerPageOptions={[10, 25, 100]}
                component="div"
                count={rows.length}
                rowsPerPage={rowsPerPage}
                page={page}
                onPageChange={handleChangePage}
                onRowsPerPageChange={handleChangeRowsPerPage}
            /> */}
        </Paper >
    );
}