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
// import { Button } from 'reactstrap';
import { Box, Typography } from '@mui/material';
import { Button, Col, Form, FormGroup, InputGroup, Row } from 'react-bootstrap';
import Modal from 'react-bootstrap/Modal';
// import Modal from '@mui/material/Modal'
import './MyClients.css';
import { TextField } from '@mui/material';
// import { Form, Label, FormGroup, Input, CloseButton } from 'reactstrap'
import { Helmet } from 'react-helmet';
import { BsCashCoin, BsCheck2Square, BsEnvelopeFill, BsHouseFill, BsInfoCircle, BsInfoLg, BsKey, BsKeyFill, BsMailbox, BsPerson, BsPersonCheck, BsPersonCheckFill, BsPersonFill, BsPersonX, BsPhoneFill, BsPlusCircleFill } from 'react-icons/bs';


const columns = [

    {
        id: 'agentNo',
        label: 'Agent No',
        minWidth: 170,
        align: 'right',
        format: (value) => value.toLocaleString('en-US'),
    },
    {
        id: 'username',
        label: 'User Name',
        minWidth: 170,
        align: 'right',
        format: (value) => value.toLocaleString('en-US'),
    },
    {
        id: 'agentNames',
        label: 'Agent Names',
        minWidth: 170,
        align: 'right',
        format: (value) => value.toLocaleString('en-US'),
    },
    {
        id: 'phone',
        label: 'Mobile Phone',
        minWidth: 170,
        align: 'right',
        format: (value) => value.toLocaleString('en-US'),
    },
    {
        id: 'accountBal',
        label: 'Account Bal.(Rwf)',
        minWidth: 170,
        align: 'right',
        format: (value) => value.toLocaleString('en-US'),
    },
    {
        id: 'active',
        label: 'Active',
        minWidth: 170,
        align: 'right',
        format: (value) => value.toLocaleString('en-US'),
    },
    {
        id: 'lastLogin',
        label: 'Last Login',
        minWidth: 170,
        align: 'right',
        format: (value) => value.toLocaleString('en-US'),
    },
    {
        id: 'dateJoined',
        label: 'Date Joined',
        minWidth: 170,
        align: 'right',
        format: (value) => value.toLocaleString('en-US'),
    },
];

function createData(agentNo, username, agentNames, phone, accountBal, active, lastLogin, dateJoined) {
    return { agentNo, username, agentNames, phone, accountBal, active, lastLogin, dateJoined };
}

const rows = [
    createData('0000006758', 'Rosine', 'Rosine', '0785467789', 5000, '1', 34567890, 34485990),
    createData('0000006758', 'Rosine', 'Rosine', '0785467789', 5000, '1', 34567890, 34485990),
    createData('0000006758', 'Rosine', 'Rosine', '0785467789', 5000, '1', 34567890, 34485990),

];

export default function MyCreditTransfers() {
    const [page, setPage] = React.useState(0);
    const [rowsPerPage, setRowsPerPage] = React.useState(10);
    // const [open, setOpen] = React.useState(false)
    // const handleOpen = () => setOpen(true)
    // const handleClose = () => setOpen(false)


    const [show, setShow] = React.useState(false)

    const handleClose = () => setShow(false)
    const handleShow = () => setShow(true)

    const handleChangePage = (event, newPage) => {
        setPage(newPage);
    };

    const handleChangeRowsPerPage = (event) => {
        setRowsPerPage(+event.target.value);
        setPage(0);
    };

    // refresh

    const refresh = () => window.location.reload(true)



    return (
        <Paper sx={{ width: '80%', margin: '1rem', overflowX: 'auto' }}>


            <Stack spacing={2} direction="row" sx={{ margin: "1rem", paddingTop: "1rem" }}>
                <Button onClick={handleShow} className="app-buttons mt-0 text-dark ">ADD</Button>
                {/* <Button variant="outlined" className='app-buttons text-dark mt-0' color='secondary' onClick={handleOpen}>Add</Button> */}
                {/* <Modal
                    keepMounted
                    open={open}
                    onClose={handleClose}
                    aria-labelledby="keep-mounted-modal-title"
                    aria-describedby="keep-mounted-modal-body"

                >
                    <Box className='my-clients-modal' sx>
                        <div className="modal-title">
                            <Typography>Add Client</Typography>
                        </div>

                        <div className='modal-form'>
                            <Stack>
                                <Form>
                                    <Stack direction="row" gap={1} className='form-row'>
                                        <FormGroup>
                                            <TextField
                                                id="outlined-input"
                                                label="Customer No."
                                                size='small'
                                                className='modal-input'
                                                inputProps={{
                                                    readOnly: true
                                                }}

                                            />
                                        </FormGroup>
                                        <FormGroup>
                                            <TextField
                                                id="outlined-input"
                                                label="Names"
                                                size='small'
                                                className='modal-input'

                                            />
                                        </FormGroup>
                                    </Stack>
                                    <Stack direction="row" gap={1}>
                                        <FormGroup>
                                            <TextField
                                                id="outlined-input"
                                                label="User Name"
                                                size='small'
                                                className='modal-input'

                                            />
                                        </FormGroup>
                                        <FormGroup>
                                            <TextField
                                                id="outlined-input"
                                                label="Active"
                                                size='small'
                                                className='modal-input'
                                                inputProps={{
                                                    readOnly: true
                                                }}

                                            />
                                        </FormGroup>
                                    </Stack>
                                    <Stack direction="row" gap={1}>
                                        <FormGroup>
                                            <TextField
                                                id="outlined-input"
                                                label="Mobile Phone"
                                                size='small'
                                                className='modal-input'

                                            />
                                        </FormGroup>
                                        <FormGroup>
                                            <TextField
                                                id="outlined-input"
                                                label="E-mail"
                                                size='small'
                                                className='modal-input'

                                            />
                                        </FormGroup>
                                    </Stack>
                                    <Stack direction="row" gap={1}>
                                        <FormGroup>
                                            <TextField
                                                id="outlined-input"
                                                label="Address"
                                                size='small'
                                                className='modal-input'

                                            />
                                        </FormGroup>
                                        <FormGroup>
                                            <TextField
                                                id="outlined-input"
                                                label="Account Bal"
                                                size='small'
                                                className='modal-input'
                                                inputProps={{
                                                    readOnly: true
                                                }}

                                            />
                                        </FormGroup>
                                    </Stack>

                                    <Stack direction="row" gap={1}>
                                        <FormGroup>
                                            <TextField
                                                id="outlined-input"
                                                label="Comission"
                                                size='small'
                                                className='modal-input'
                                                inputProps={{
                                                    readOnly: true
                                                }}

                                            />
                                        </FormGroup>
                                        <FormGroup>
                                            <TextField
                                                id="outlined-input"
                                                label="Description"
                                                size='small'
                                                className='modal-input'

                                            />
                                        </FormGroup>
                                    </Stack>
                                    <Stack direction="row" gap={1}>
                                        <FormGroup>
                                            <TextField
                                                id="outlined-input"
                                                label="Password"
                                                size='small'
                                                className='modal-input'

                                            />
                                        </FormGroup>
                                        <FormGroup>
                                            <TextField
                                                id="outlined-input"
                                                label="Created"
                                                size='small'
                                                className='modal-input'
                                                inputProps={{
                                                    readOnly: true
                                                }}

                                            />
                                        </FormGroup>
                                    </Stack>
                                    <Stack direction="row" gap={1}>
                                        <FormGroup>
                                            <TextField
                                                id="outlined-input"
                                                label="Confirm Password"
                                                size='small'
                                                className='modal-input'

                                            />
                                        </FormGroup>
                                        <FormGroup>
                                            <TextField
                                                id="outlined-input"
                                                label="Last Login"
                                                size='small'
                                                className='modal-input'
                                                inputProps={{
                                                    readOnly: true
                                                }}

                                            />
                                        </FormGroup>
                                    </Stack>



                                    <Stack direction="row" gap={1}>
                                        <button className='app-modal-buttons'>Save and Close</button>
                                        <button className='app-modal-buttons'>Save and New</button>
                                        <button className='app-modal-buttons'>Cancel</button>
                                    </Stack>

                                </Form>
                            </Stack>
                        </div>

                    </Box>


                </Modal > */}


                {/* Add Modal */}

                <Modal show={show} onHide={handleClose} className="modal">
                    <Modal.Header className='modal-header' closeButton>
                        <span className='contact-modal-title'><BsPlusCircleFill/> ADD CLIENT</span>
                    </Modal.Header>
                    <Modal.Body>
                        <Form >

                            <div className='d-flex flex-row mb-3 gap-2'>
                                <InputGroup>
                                    {/* <Form.Label>New Password</Form.Label> */}
                                    <InputGroup.Text>
                                    <BsPersonCheckFill/>
                                    </InputGroup.Text>
                                    <Form.Control type='text' placeholder='Customer No' required></Form.Control>
                                </InputGroup>

                                <InputGroup>
                                    {/* <Form.Label>New Password</Form.Label> */}
                                    <InputGroup.Text>
                                        <BsPerson/>
                                    </InputGroup.Text>
                                    <Form.Control type='text' placeholder='Names' required></Form.Control>
                                </InputGroup>
                            </div>
                            <div className='d-flex flex-row mb-3 gap-2'>
                                <InputGroup>
                                    {/* <Form.Label>New Password</Form.Label> */}
                                    <InputGroup.Text>
                                    <BsPersonFill/>
                                    </InputGroup.Text>
                                    <Form.Control type='text' placeholder='User Name' required></Form.Control>
                                </InputGroup>

                                <InputGroup>
                                    {/* <Form.Label>New Password</Form.Label> */}
                                    <InputGroup.Text>
                                        <BsPersonCheck/>
                                    </InputGroup.Text>
                                    <Form.Control type='text' placeholder='Active' required></Form.Control>
                                </InputGroup>
                            </div>

                            <div className='d-flex flex-row mb-3 gap-2'>
                                <InputGroup>
                                    {/* <Form.Label>New Password</Form.Label> */}
                                    <InputGroup.Text>
                                    <BsPhoneFill/>
                                    </InputGroup.Text>
                                    <Form.Control type='text' placeholder='Mobile Phone' required></Form.Control>
                                </InputGroup>

                                <InputGroup>
                                    {/* <Form.Label>New Password</Form.Label> */}
                                    <InputGroup.Text>
                                        <BsEnvelopeFill/>
                                    </InputGroup.Text>
                                    <Form.Control type='text' placeholder='E-mail' required></Form.Control>
                                </InputGroup>
                            </div>

                            <div className='d-flex flex-row mb-3 gap-2'>
                                <InputGroup>
                                    {/* <Form.Label>New Password</Form.Label> */}
                                    <InputGroup.Text>
                                    <BsHouseFill/>
                                    </InputGroup.Text>
                                    <Form.Control type='text' placeholder='Address' required></Form.Control>
                                </InputGroup>

                                <InputGroup>
                                    {/* <Form.Label>New Password</Form.Label> */}
                                    <InputGroup.Text>
                                        <BsCashCoin/>
                                    </InputGroup.Text>
                                    <Form.Control type='text' placeholder='Account Bal' required></Form.Control>
                                </InputGroup>
                            </div>

                            <div className='d-flex flex-row mb-3 gap-2'>
                                <InputGroup>
                                    {/* <Form.Label>New Password</Form.Label> */}
                                    <InputGroup.Text>
                                    <BsCashCoin/>
                                    </InputGroup.Text>
                                    <Form.Control type='text' placeholder='Commission' required></Form.Control>
                                </InputGroup>

                                <InputGroup>
                                    {/* <Form.Label>New Password</Form.Label> */}
                                    <InputGroup.Text>
                                        <BsInfoCircle/>
                                    </InputGroup.Text>
                                    <Form.Control type='text' placeholder='Description' required></Form.Control>
                                </InputGroup>
                            </div>

                            <div className='d-flex flex-row mb-3 gap-2'>
                                <InputGroup>
                                    {/* <Form.Label>New Password</Form.Label> */}
                                    <InputGroup.Text>
                                    <BsKey/>
                                    </InputGroup.Text>
                                    <Form.Control type='text' placeholder='Password' required></Form.Control>
                                </InputGroup>

                                <InputGroup>
                                    {/* <Form.Label>New Password</Form.Label> */}
                                    <InputGroup.Text>
                                        <BsCheck2Square/>
                                    </InputGroup.Text>
                                    <Form.Control type='text' placeholder='Created' required></Form.Control>
                                </InputGroup>
                            </div>

                            <div className='d-flex flex-row mb-3 gap-2'>
                                <InputGroup>
                                    {/* <Form.Label>New Password</Form.Label> */}
                                    <InputGroup.Text>
                                    <BsKeyFill/>
                                    </InputGroup.Text>
                                    <Form.Control type='text' placeholder='Confirm password' required></Form.Control>
                                </InputGroup>

                                <InputGroup>
                                    {/* <Form.Label>New Password</Form.Label> */}
                                    <InputGroup.Text>
                                        <BsInfoLg/>
                                    </InputGroup.Text>
                                    <Form.Control type='text' placeholder='Last Login' required></Form.Control>
                                </InputGroup>
                            </div>





                        </Form>
                    </Modal.Body>
                    <Modal.Footer>

                        <Button className='btn btn-dark opacity-60'>Save Changes</Button>

                        <Button className='btn btn-dark opacity-60' onClick={handleClose}> Cancel</Button>

                    </Modal.Footer>

                </Modal>


                {/* end modal */}


                <button className='app-buttons' variant="contained" color='dark'>View & Modify</button>
                <button className='app-buttons' onClick={refresh} variant="contained" color='dark'>Refresh</button>
            </Stack >
            <TableContainer sx={{ maxHeight: 440 }}>
                <Table stickyHeader aria-label="sticky table">
                    <TableHead className='my-clients-table'>

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
                                                    {column.format && typeof value === 'text'
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
            />
        </Paper >
    );
}