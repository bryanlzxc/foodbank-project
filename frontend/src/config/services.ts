import * as env from './../environments/environment.prod';

const prod_services = {
    'test-service': 'https://httpbin.org/get',
    'admin': {
        'dashboard':'',
        'allocation':'',
        'inventory':'',
        'packing-list':''
    },
    'volunteer': {
        'dashboard':'',
        'inventory':'',
        'packing-list':''
    },
    'beneficiary': {
        'dashboard':'',
        'inventory':'',
        'request':''
    }
};

const dev_services = {
    'test-service': './../../assets/mock/test-service.json',
    'admin': {
        'dashboard':'',
        'allocation':'',
        'inventory':'',
        'packing-list':''
    },
    'volunteer': {
        'dashboard':'',
        'inventory':'',
        'packing-list':''
    },
    'beneficiary': {
        'dashboard':'',
        'inventory':'',
        'request':''
    }
};

const services = env.environment.production ? prod_services : dev_services;

export default services;
